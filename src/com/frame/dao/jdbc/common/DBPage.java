package com.frame.dao.jdbc.common;

import java.util.List;

/**
 * 描述：数据库分页对象
 * 版权：Copyright (c) 2015
 * 时间：2015/1/25 16:52
 * 公司：中国联通
 * 作者：杨黎明
 * 版本：1.0
 */
public class DBPage {
    public static final int NUMBERS_PER_PAGE = 10;
    private int numPerPage;
    private int totalRows;
    private int totalPages;
    private int currentPage;
    private int startIndex;
    private int lastIndex;
    private List dataList;
    private boolean firstFlag = true;
    private boolean prevFlag = true;
    private boolean nextFlag = true;
    private boolean lastFlag = true;
    private String buttonType = "";

    public DBPage(int currentPage, int numPerPage) {
        this.numPerPage = numPerPage;
        this.currentPage = currentPage;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
        this.calcTotalPages();
        this.calcStartIndex();
        this.calcLastIndex();
        this.setPageButton();
    }

    private void setPageButton() {
        if(this.totalPages != 0 && this.totalPages != 1) {
            if(this.currentPage > this.totalPages) {
                this.currentPage = this.totalPages;
            }

            if(this.currentPage <= 1 || this.currentPage == 0) {
                this.firstFlag = false;
                this.prevFlag = false;
            }

            if(this.currentPage >= this.totalPages || this.totalPages == 0) {
                this.lastFlag = false;
                this.nextFlag = false;
            }

        } else {
            this.firstFlag = false;
            this.prevFlag = false;
            this.nextFlag = false;
            this.lastFlag = false;
        }
    }

    private void calcTotalPages() {
        if(this.totalRows % this.numPerPage == 0) {
            this.totalPages = this.totalRows / this.numPerPage;
        } else {
            this.totalPages = this.totalRows / this.numPerPage + 1;
        }

    }

    private void calcStartIndex() {
        this.startIndex = (this.currentPage - 1) * this.numPerPage;
    }

    private void calcLastIndex() {
        if(this.totalRows < this.numPerPage) {
            this.lastIndex = this.totalRows;
        } else if(this.totalRows % this.numPerPage != 0 && (this.totalRows % this.numPerPage == 0 || this.currentPage >= this.totalPages)) {
            if(this.totalRows % this.numPerPage != 0 && this.currentPage == this.totalPages) {
                this.lastIndex = this.totalRows;
            }
        } else {
            this.lastIndex = this.currentPage * this.numPerPage;
        }

    }

    private String renderButton(String show, boolean disabled, int page) {
        if(this.buttonType.equalsIgnoreCase("text")) {
            return this.renderText(show, disabled, page);
        } else {
            String temp = "";
            if(disabled) {
                temp = "disabled";
            }

            return "<input type=\"submit\" class=\"pageButton\" value=\"" + show + "\" onclick=\"goToPage(" + page + ",this.form)\" " + temp + ">\n";
        }
    }

    private String renderText(String show, boolean disabled, int page) {
        return disabled?show:"<a class=\"pageLink\" href=\"javascript:toPage(" + page + ")\">" + show + "</a>";
    }

    public String first(String show) {
        return !this.firstFlag?this.renderButton(show, true, 0):this.renderButton(show, false, 1);
    }

    public String preview(String show) {
        return !this.prevFlag?this.renderButton(show, true, 0):this.renderButton(show, false, this.currentPage - 1);
    }

    public String next(String show) {
        return !this.nextFlag?this.renderButton(show, true, 0):this.renderButton(show, false, this.currentPage + 1);
    }

    public String last(String show) {
        return !this.lastFlag?this.renderButton(show, true, 0):this.renderButton(show, false, this.totalPages);
    }

    public void setButtonType(String buttonType) {
        this.buttonType = buttonType;
    }

    public int getCurrentPage() {
        return this.currentPage;
    }

    public int getNumPerPage() {
        return this.numPerPage;
    }

    public List getData() {
        return this.dataList != null && this.dataList.size() > this.getNumPerPage()?this.dataList.subList(this.getStartIndex(), this.getLastIndex()):this.dataList;
    }

    public int getTotalPages() {
        return this.totalPages;
    }

    public int getTotalRows() {
        return this.totalRows;
    }

    public int getStartIndex() {
        return this.startIndex;
    }

    public int getLastIndex() {
        return this.lastIndex;
    }

    public void setData(List dataList) {
        this.dataList = dataList;
    }

    public String getLinkStr(String url, String path) {
        String linkStr = "";
        String scriptTmp = "";
        int pageNumber = this.currentPage;
        int pages = this.totalPages;
        int total = this.totalRows;
        linkStr = linkStr + "共 <b>" + total + "</b> 条&nbsp;当前 <b>" + pageNumber + "</b>/<b>" + pages + "</b> 页&nbsp;&nbsp;";
        if(url.indexOf("?") > 0) {
            url = url + "&";
        } else {
            url = url + "?";
        }

        if(pageNumber == 1 && pageNumber < pages) {
            linkStr = linkStr + "首页&nbsp;上一页&nbsp;<a href=\'" + url + "&pageNumber=" + (pageNumber + 1) + "\'>下一页</a>&nbsp;<a href=\'" + url + "&pageNumber=" + pages + "\'>尾页</a>&nbsp;跳到<input type=text name=jump id=jump size=3 style=\"text-align:center\" value=\'" + pageNumber + "\'>页&nbsp;<img border=\"0\" src=\"" + path + "/images/button020.gif\" width=\"20\" height=\"18\"  style=\"cursor:hand;\" onclick=\"javascript:checkPage();\">";
        } else if(pageNumber > 1 && pageNumber < pages) {
            linkStr = linkStr + "<a href=\'" + url + "&pageNumber=1\'>首页</a>&nbsp;<a href=\'" + url + "&pageNumber=" + (pageNumber - 1) + "\'>上一页</a>&nbsp;<a href=\'" + url + "&pageNumber=" + (pageNumber + 1) + "\'>下一页&nbsp;<a href=\'" + url + "&pageNumber=" + pages + "\'>尾页</a>&nbsp;跳到<input type=text name=jump id=jump size=3 style=\"text-align:center\" value=\'" + pageNumber + "\'>页&nbsp;<img border=\"0\" src=\"" + path + "/images/button020.gif\" width=\"20\" height=\"18\"  style=\"cursor:hand;\" onclick=\"javascript:checkPage();\">";
        } else if(pageNumber == pages && pages > 1) {
            linkStr = linkStr + "<a href=\'" + url + "&pageNumber=1\'>首页</a>&nbsp;<a href=\'" + url + "&pageNumber=" + (pageNumber - 1) + "\'>上一页</a>&nbsp;下一页&nbsp;尾页&nbsp;跳到<input type=text name=jump id=jump size=3 style=\"text-align:center\" value=\'" + pageNumber + "\'>页&nbsp;<img border=\"0\" src=\"" + path + "/images/button020.gif\" width=\"20\" height=\"18\" style=\"cursor:hand;\" onclick=\"javascript:checkPage();\">";
        }

        scriptTmp = scriptTmp + "<SCRIPT LANGUAGE=\"JavaScript\">\n<!--\nfunction checkPage()\n {\n if(document.getElementById(\"jump\").value > " + pages + ") {\n alert(\'您输入的页码超出范围，请重新输入！\');\n document.getElementById(\'jump\').focus();\n return false;\n} else if(document.getElementById(\'jump\').value < 1) {\n alert(\'您输入的页码超出范围，请重新输入！\');\n document.getElementById(\'jump\').focus();\n return false;\n} else {\n jumpTo(\'" + url + "\');\n}\n}\n//-->\n</SCRIPT>";
        scriptTmp = scriptTmp + "<SCRIPT LANGUAGE=\"JavaScript\">\n<!--\nfunction jumpTo(url)\n {\n self.location.href=\"" + url + "&pageNumber=\"+" + "document.getElementById(\"jump\").value;\n}\n//-->\n</SCRIPT>";
        linkStr = linkStr + scriptTmp;
        return linkStr;
    }
}
