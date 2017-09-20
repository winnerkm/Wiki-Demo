package com.wikidemo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by winnerkm on 19/09/17.
 */

public class WikiResponse {
    @SerializedName("continue")
    @Expose
    private Continue _continue;
    @SerializedName("query")
    @Expose
    private Query query;

    public Continue getContinue() {
        return _continue;
    }

    public void setContinue(Continue _continue) {
        this._continue = _continue;
    }

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public class Thumbnail {

        @SerializedName("source")
        @Expose
        private String source;
        @SerializedName("width")
        @Expose
        private Integer width;
        @SerializedName("height")
        @Expose
        private Integer height;

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public Integer getWidth() {
            return width;
        }

        public void setWidth(Integer width) {
            this.width = width;
        }

        public Integer getHeight() {
            return height;
        }

        public void setHeight(Integer height) {
            this.height = height;
        }
    }

    public class Terms {

        @SerializedName("description")
        @Expose
        private List<String> description = null;

        public List<String> getDescription() {
            return description;
        }

        public void setDescription(List<String> description) {
            this.description = description;
        }

    }


    public class Redirect {

        @SerializedName("index")
        @Expose
        private Integer index;
        @SerializedName("from")
        @Expose
        private String from;
        @SerializedName("to")
        @Expose
        private String to;

        public Integer getIndex() {
            return index;
        }

        public void setIndex(Integer index) {
            this.index = index;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }
    }


    public class Query {

        @SerializedName("redirects")
        @Expose
        private List<Redirect> redirects = null;
        @SerializedName("pages")
        @Expose
        private List<Page> pages = null;

        public List<Redirect> getRedirects() {
            return redirects;
        }

        public void setRedirects(List<Redirect> redirects) {
            this.redirects = redirects;
        }

        public List<Page> getPages() {
            return pages;
        }

        public void setPages(List<Page> pages) {
            this.pages = pages;
        }

    }

    public class Page {

        @SerializedName("pageid")
        @Expose
        private Integer pageid;
        @SerializedName("ns")
        @Expose
        private Integer ns;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("index")
        @Expose
        private Integer index;
        @SerializedName("terms")
        @Expose
        private Terms terms;
        @SerializedName("thumbnail")
        @Expose
        private Thumbnail thumbnail;

        public Integer getPageid() {
            return pageid;
        }

        public void setPageid(Integer pageid) {
            this.pageid = pageid;
        }

        public Integer getNs() {
            return ns;
        }

        public void setNs(Integer ns) {
            this.ns = ns;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Integer getIndex() {
            return index;
        }

        public void setIndex(Integer index) {
            this.index = index;
        }

        public Terms getTerms() {
            return terms;
        }

        public void setTerms(Terms terms) {
            this.terms = terms;
        }

        public Thumbnail getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(Thumbnail thumbnail) {
            this.thumbnail = thumbnail;
        }
    }

    public class Continue {

        @SerializedName("picontinue")
        @Expose
        private Integer picontinue;
        @SerializedName("continue")
        @Expose
        private String _continue;

        public Integer getPicontinue() {
            return picontinue;
        }

        public void setPicontinue(Integer picontinue) {
            this.picontinue = picontinue;
        }

        public String getContinue() {
            return _continue;
        }

        public void setContinue(String _continue) {
            this._continue = _continue;
        }
    }
}
