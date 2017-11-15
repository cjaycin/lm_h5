package com.frame.comm.log4j;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.helpers.CountingQuietWriter;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.helpers.OptionConverter;
import org.apache.log4j.spi.ErrorCode;
import org.apache.log4j.spi.LoggingEvent;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 描述：Log4j日志格式
 * 版权：Copyright (c) 2015
 * 时间：2015/1/11 10:30
 * 公司：中国联通
 * 作者：杨黎明
 * 版本：1.0
 */
public class TimeSizeRollingFileAppender extends FileAppender implements ErrorCode {
    private static String BACKUP_SUFFIX = ".bak";// 备份文件后缀名
    private static String LOG_ROOT_PATH = ""; // log文件根路径

    // 设置log文件根路径
    public static void setLogRootPath(String logRootPath) {
        LOG_ROOT_PATH = logRootPath;
    }

    // 返回log文件根路径.
    public static String getLogRootPath() {
        return LOG_ROOT_PATH;
    }

    protected long maxFileSize = 10 * 1024 * 1024; // The default maximum file

    protected int maxBackupIndex = 1; // here is one backup file by default.

    static final int TOP_OF_TROUBLE = -1;

    static final int TOP_OF_MINUTE = 0;

    static final int TOP_OF_HOUR = 1;

    static final int HALF_DAY = 2;

    static final int TOP_OF_DAY = 3;

    static final int TOP_OF_WEEK = 4;

    static final int TOP_OF_MONTH = 5;

    private String datePattern = "'.'yyyy-MM-dd";

    private String scheduledFilename;

    private long nextCheck = System.currentTimeMillis() - 1;

    Date now = new Date();

    SimpleDateFormat sdf;

    RollingCalendar rc = new RollingCalendar();

    int checkPeriod = TOP_OF_TROUBLE;

    static final TimeZone gmtTimeZone = TimeZone.getTimeZone("GMT");

    private File currFile;

    public TimeSizeRollingFileAppender() {
    }

    public TimeSizeRollingFileAppender(Layout layout, String filename,
                                       String datePattern) throws IOException {
        super(layout, filename, true);
        this.datePattern = datePattern;
        activateOptions();
    }

    public void setDatePattern(String pattern) {
        datePattern = pattern;
    }

    public String getDatePattern() {
        return datePattern;
    }

    public int getMaxBackupIndex() {
        return maxBackupIndex;
    }

    public long getMaximumFileSize() {
        return maxFileSize;
    }

    public void setFile(String file) {
        String val = file.trim();
        String tmpfileName = val.replace('/', File.separatorChar);

        fileName = getLogRootPath() + File.separator + tmpfileName;

        LogLog.debug("fileName:" + fileName);

        int index = fileName.lastIndexOf(File.separatorChar);
        if (index > 0) {
            String sPath = fileName.substring(0, index);
            File path = new File(sPath);
            if (!path.exists()) {
                path.mkdirs();
            }
        }

        LogLog.debug("File set:" + fileName);
    }

    public synchronized void setFile(String pFileName, boolean append, boolean bufferedIO, int bufferSize) throws IOException {
        try {
            reset();
            this.fileName = pFileName;
            LogLog.debug("setFile called: " + fileName + ", " + append);
            if (bufferedIO) {
                setImmediateFlush(false);
            }

            Writer fw = createWriter(new FileOutputStream(fileName, append));
            if (bufferedIO) {
                fw = new BufferedWriter(fw, bufferSize);
            }
            this.setQWForFiles(fw);
            this.fileAppend = append;
            this.bufferedIO = bufferedIO;
            this.bufferSize = bufferSize;
            writeHeader();

            if (append) {
                currFile = new File(fileName);
                ((CountingQuietWriter) qw).setCount(currFile.length());
            }
            LogLog.debug("setFile ended");
        } catch (IOException e) {
            errorHandler.error("Create log File error", e, FILE_OPEN_FAILURE);
        }
    }

    public void activateOptions() {
        super.activateOptions();
        if (datePattern != null && fileName != null) {
            now.setTime(System.currentTimeMillis());
            sdf = new SimpleDateFormat(datePattern);
            int type = computeCheckPeriod();
            printPeriodicity(type);
            rc.setType(type);
            currFile = new File(fileName);
            scheduledFilename = fileName + sdf.format(new Date(currFile.lastModified()));
            LogLog.debug("scheduledFilename generated:" + scheduledFilename);
        } else {
            LogLog.error("Either File or DatePattern options are not set for appender [" + name + "].");
        }
    }

    public void setMaxBackupIndex(int maxBackups) {
        this.maxBackupIndex = maxBackups;
    }

    public void setMaxFileSize(String value) {
        maxFileSize = OptionConverter.toFileSize(value, maxFileSize + 1);
    }

    public void setMaximumFileSize(long value) {
        maxFileSize = value;
    }

    protected void setQWForFiles(Writer writer) {
        this.qw = new CountingQuietWriter(writer, errorHandler);
    }

    void printPeriodicity(int type) {
        switch (type) {
            case TOP_OF_MINUTE:
                LogLog.debug("Appender [" + name + "] to be rolled every minute.");
                break;
            case TOP_OF_HOUR:
                LogLog.debug("Appender [" + name + "] to be rolled on top of every hour.");
                break;
            case HALF_DAY:
                LogLog.debug("Appender [" + name + "] to be rolled at midday and midnight.");
                break;
            case TOP_OF_DAY:
                LogLog.debug("Appender [" + name + "] to be rolled at midnight.");
                break;
            case TOP_OF_WEEK:
                LogLog.debug("Appender [" + name + "] to be rolled at start of week.");
                break;
            case TOP_OF_MONTH:
                LogLog.debug("Appender [" + name + "] to be rolled at start of every month.");
                break;
            default:
                LogLog.warn("Unknown periodicity for appender [" + name + "].");
        }
    }

    int computeCheckPeriod() {
        RollingCalendar rollingCalendar = new RollingCalendar(gmtTimeZone,
                Locale.ENGLISH);
        // set sate to 1970-01-01 00:00:00 GMT
        Date epoch = new Date(0);
        if (datePattern != null) {
            for (int i = TOP_OF_MINUTE; i <= TOP_OF_MONTH; i++) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
                simpleDateFormat.setTimeZone(gmtTimeZone);
                String r0 = simpleDateFormat.format(epoch);
                rollingCalendar.setType(i);
                Date next = new Date(rollingCalendar.getNextCheckMillis(epoch));
                String r1 = simpleDateFormat.format(next);
                LogLog.debug("Type = " + i + ", r0 = " + r0 + ", r1 = " + r1);
                if (r0 != null && r1 != null && !r0.equals(r1)) {
                    return i;
                }
            }
        }
        return TOP_OF_TROUBLE; // Deliberately head for trouble...
    }

    public void rollOverForTime() throws IOException {

        if (datePattern == null) {
            errorHandler.error("Missing DatePattern option in rollOver().");
            return;
        }

        String datedFilename = fileName + sdf.format(now);
        if (scheduledFilename.equals(datedFilename)) {
            return;
        }

        this.closeFile();

        File target = new File(scheduledFilename + BACKUP_SUFFIX);
        if (target.exists()) {
            target.delete();
        }

        File file = new File(fileName);
        for (int i = 1; i <= maxBackupIndex; i++) { // roll for all size-backup
            String before = fileName + "." + i;
            File files = new File(before);
            String after = scheduledFilename + "." + i + BACKUP_SUFFIX;
            File targets = new File(after);
            if (targets.exists()) {
                targets.delete();
            }
            if (files.exists()) {
                boolean result = files.renameTo(targets);
                if (result) {
                    LogLog.debug(before + " -> " + after);
                } else {
                    LogLog.error("Failed to rename [" + before + "] to [" + after + "].");
                }
            }
        }

        boolean result = file.renameTo(target);
        if (result) {
            LogLog.debug(fileName + " -> " + scheduledFilename);
        } else {
            LogLog.error("Failed to rename [" + fileName + "] to [" + scheduledFilename + "].");
        }

        try {
            this.setFile(fileName, false, this.bufferedIO, this.bufferSize);
        } catch (IOException e) {
            errorHandler.error("setFile(" + fileName + ", false) call failed.");
        }
        scheduledFilename = datedFilename;
        LogLog.debug("scheduledFilename after roll:" + scheduledFilename);
    }

    public void rollOverForSize() {
        File target;
        File file;

        LogLog.debug("rolling over count="
                + ((CountingQuietWriter) qw).getCount());
        LogLog.debug("maxBackupIndex=" + maxBackupIndex);
        if (maxBackupIndex > 0) {
            file = new File(fileName + '.' + maxBackupIndex);
            if (file.exists()) {
                file.delete();
            }
            for (int i = maxBackupIndex - 1; i >= 1; i--) {
                file = new File(fileName + "." + i);
                if (file.exists()) {
                    target = new File(fileName + '.' + (i + 1));
                    LogLog.debug("Renaming file " + file + " to " + target);
                    file.renameTo(target);
                }
            }

            target = new File(fileName + "." + 1);
            this.closeFile();
            file = new File(fileName);
            LogLog.debug("Renaming file " + file + " to " + target);
            file.renameTo(target);
        }

        try {
            this.setFile(fileName, false, bufferedIO, bufferSize);
        } catch (IOException e) {
            LogLog.error("setFile(" + fileName + ", false) call failed.", e);
        }
    }

    protected void subAppend(LoggingEvent event) {
        if ((fileName != null)
                && ((CountingQuietWriter) qw).getCount() >= maxFileSize) {
            rollOverForSize();
        }
        long n = System.currentTimeMillis();
        if (n >= nextCheck) {
            now.setTime(n);
            nextCheck = rc.getNextCheckMillis(now);
            try {
                rollOverForTime();
            } catch (IOException ioe) {
                LogLog.error("rollOver() failed.", ioe);
            }
        }
        super.subAppend(event);
    }
}

class RollingCalendar extends GregorianCalendar {
    private static final long serialVersionUID = 2238807139240434580L;

    int type = TimeSizeRollingFileAppender.TOP_OF_TROUBLE;

    RollingCalendar() {
        super();
    }

    RollingCalendar(TimeZone tz, Locale locale) {
        super(tz, locale);
    }

    void setType(int type) {
        this.type = type;
    }

    public long getNextCheckMillis(Date now) {
        return getNextCheckDate(now).getTime();
    }

    public Date getNextCheckDate(Date now) {
        this.setTime(now);

        switch (type) {
            case TimeSizeRollingFileAppender.TOP_OF_MINUTE:
                this.set(Calendar.SECOND, 0);
                this.set(Calendar.MILLISECOND, 0);
                this.add(Calendar.MINUTE, 1);
                break;
            case TimeSizeRollingFileAppender.TOP_OF_HOUR:
                this.set(Calendar.MINUTE, 0);
                this.set(Calendar.SECOND, 0);
                this.set(Calendar.MILLISECOND, 0);
                this.add(Calendar.HOUR_OF_DAY, 1);
                break;
            case TimeSizeRollingFileAppender.HALF_DAY:
                this.set(Calendar.MINUTE, 0);
                this.set(Calendar.SECOND, 0);
                this.set(Calendar.MILLISECOND, 0);
                int hour = get(Calendar.HOUR_OF_DAY);
                if (hour < 12) {
                    this.set(Calendar.HOUR_OF_DAY, 12);
                } else {
                    this.set(Calendar.HOUR_OF_DAY, 0);
                    this.add(Calendar.DAY_OF_MONTH, 1);
                }
                break;
            case TimeSizeRollingFileAppender.TOP_OF_DAY:
                this.set(Calendar.HOUR_OF_DAY, 0);
                this.set(Calendar.MINUTE, 0);
                this.set(Calendar.SECOND, 0);
                this.set(Calendar.MILLISECOND, 0);
                this.add(Calendar.DATE, 1);
                break;
            case TimeSizeRollingFileAppender.TOP_OF_WEEK:
                this.set(Calendar.DAY_OF_WEEK, getFirstDayOfWeek());
                this.set(Calendar.HOUR_OF_DAY, 0);
                this.set(Calendar.SECOND, 0);
                this.set(Calendar.MILLISECOND, 0);
                this.add(Calendar.WEEK_OF_YEAR, 1);
                break;
            case TimeSizeRollingFileAppender.TOP_OF_MONTH:
                this.set(Calendar.DATE, 1);
                this.set(Calendar.HOUR_OF_DAY, 0);
                this.set(Calendar.SECOND, 0);
                this.set(Calendar.MILLISECOND, 0);
                this.add(Calendar.MONTH, 1);
                break;
            default:
                throw new IllegalStateException("Unknown periodicity type.");
        }
        return getTime();
    }
}
