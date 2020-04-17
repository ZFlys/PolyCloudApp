package com.example.polycloudapp.beans;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MainPolymerNameBean {
    @SerializedName("gen_polymer")
    private List<GenPolymerBean> genPolymer;
    @SerializedName("special_polymer")
    private List<SpecialPolymerBean> specialPolymer;

    public List<GenPolymerBean> getGenPolymer() {
        return genPolymer;
    }

    public void setGenPolymer(List<GenPolymerBean> genPolymer) {
        this.genPolymer = genPolymer;
    }

    public List<SpecialPolymerBean> getSpecialPolymer() {
        return specialPolymer;
    }

    public void setSpecialPolymer(List<SpecialPolymerBean> specialPolymer) {
        this.specialPolymer = specialPolymer;
    }

    public static class GenPolymerBean {
        /**
         * id : 1
         * polymer_name : 聚乙烯类
         * kindnum : 30种
         * poster : category_juyixi
         */

        private int id;
        @SerializedName("polymer_name")
        private String polymerName;
        private String kindnum;
        private String poster;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPolymerName() {
            return polymerName;
        }

        public void setPolymerName(String polymerName) {
            this.polymerName = polymerName;
        }

        public String getKindnum() {
            return kindnum;
        }

        public void setKindnum(String kindnum) {
            this.kindnum = kindnum;
        }

        public String getPoster() {
            return poster;
        }

        public void setPoster(String poster) {
            this.poster = poster;
        }
    }

    public static class SpecialPolymerBean {
        /**
         * method : TPU改性
         */

        private String method;

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }
    }
/*
    @SerializedName("gen_polymer")
    private List<GenPolymerBean> genPolymer;

    public List<GenPolymerBean> getGenPolymer() {
        return genPolymer;
    }

    public void setGenPolymer(List<GenPolymerBean> genPolymer) {
        this.genPolymer = genPolymer;
    }

    public static class GenPolymerBean {
        /**
         * id : 1
         * polymer_name : 聚乙烯类
         * kindnum : 30种
         * poster : @mipmap/category_juyixi
         */
/*
        private int id;
        @SerializedName("polymer_name")
        private String polymerName;
        @SerializedName("kindnum")
        private String kindNum;
        private String poster;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPolymerName() {
            return polymerName;
        }

        public void setPolymerName(String polymerName) {
            this.polymerName = polymerName;
        }

        public String getKindNum() {
            return kindNum;
        }

        public void setKindNum(String kindNum) {
            this.kindNum = kindNum;
        }

        public String getPoster() {
            return poster;
        }

        public void setPoster(String poster) {
            this.poster = poster;
        }
    }
    */
}
