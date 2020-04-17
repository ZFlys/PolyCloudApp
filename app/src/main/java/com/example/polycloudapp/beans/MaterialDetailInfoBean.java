package com.example.polycloudapp.beans;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MaterialDetailInfoBean {

    private List<MaterialDetailBean> materialDetail;
    private List<List<String>> rlScatterData;
    private List<List<String>> rlFitData;
    private List<String> materialUt;
    private List<String> materialNir;
    private List<String> materialRaman;

    public List<MaterialDetailBean> getMaterialDetail() {
        return materialDetail;
    }

    public void setMaterialDetail(List<MaterialDetailBean> materialDetail) {
        this.materialDetail = materialDetail;
    }

    public List<List<String>> getRlScatterData() {
        return rlScatterData;
    }

    public void setRlScatterData(List<List<String>> rlScatterData) {
        this.rlScatterData = rlScatterData;
    }

    public List<List<String>> getRlFitData() {
        return rlFitData;
    }

    public void setRlFitData(List<List<String>> rlFitData) {
        this.rlFitData = rlFitData;
    }

    public List<String> getMaterialUt() {
        return materialUt;
    }

    public void setMaterialUt(List<String> materialUt) {
        this.materialUt = materialUt;
    }

    public List<String> getMaterialNir() {
        return materialNir;
    }

    public void setMaterialNir(List<String> materialNir) {
        this.materialNir = materialNir;
    }

    public List<String> getMaterialRaman() {
        return materialRaman;
    }

    public void setMaterialRaman(List<String> materialRaman) {
        this.materialRaman = materialRaman;
    }

    public static class MaterialDetailBean {
        /**
         * material_name : MLLDPE
         * material_no : 1018LA
         * processing_level : 薄膜级
         * feature : 优异的拉伸性能、冲击强度和穿刺性能
         * application : 农业薄膜、食品包装、吹膜材料
         * material_type : 纯料
         * density : 0.918
         * melting_point : 119
         * melt_index : 1
         * distorion_temp : 150
         * glass_tra_temp : null
         * tensile_strength : 46.1
         * tensile_modulus : null
         * elongation_at_break : 470%
         * bending_strength : null
         * bending_modulus : null
         * impact_strength : 580g
         * others :
         * manuOrComp : 埃克森美孚
         */

        @SerializedName("material_name")
        private String materialName;
        @SerializedName("material_no")
        private String materialNo;
        @SerializedName("processing_level")
        private String processingLevel;
        private String feature;
        private String application;
        @SerializedName("material_type")
        private String materialType;
        private String density;
        @SerializedName("melting_point")
        private String meltingPoint;
        @SerializedName("melt_index")
        private String  meltIndex;
        @SerializedName("distorion_temp")
        private String distorionTemp;
        @SerializedName("glass_tra_temp")
        private String  glassTraTemp;
        @SerializedName("tensile_strength")
        private String  tensileStrength;
        @SerializedName("tensile_modulus")
        private String tensileModulus;
        @SerializedName("elongation_at_break")
        private String elongationAtBreak;
        @SerializedName("bending_strength")
        private String bendingStrength;
        @SerializedName("bending_modulus")
        private String bendingModulus;
        @SerializedName("impact_strength")
        private String impactStrength;
        private String others;
        private String manuOrComp;

        public String getMaterialName() {
            return materialName;
        }

        public void setMaterialName(String materialName) {
            this.materialName = materialName;
        }

        public String getMaterialNo() {
            return materialNo;
        }

        public void setMaterialNo(String materialNo) {
            this.materialNo = materialNo;
        }

        public String getProcessingLevel() {
            return processingLevel;
        }

        public void setProcessingLevel(String processingLevel) {
            this.processingLevel = processingLevel;
        }

        public String getFeature() {
            return feature;
        }

        public void setFeature(String feature) {
            this.feature = feature;
        }

        public String getApplication() {
            return application;
        }

        public void setApplication(String application) {
            this.application = application;
        }

        public String getMaterialType() {
            return materialType;
        }

        public void setMaterialType(String materialType) {
            this.materialType = materialType;
        }

        public String getDensity() {
            return density;
        }

        public void setDensity(String density) {
            this.density = density;
        }

        public String getMeltingPoint() {
            return meltingPoint;
        }

        public void setMeltingPoint(String meltingPoint) {
            this.meltingPoint = meltingPoint;
        }

        public String  getMeltIndex() {
            return meltIndex;
        }

        public void setMeltIndex(String meltIndex) {
            this.meltIndex = meltIndex;
        }

        public String getDistorionTemp() {
            return distorionTemp;
        }

        public void setDistorionTemp(String distorionTemp) {
            this.distorionTemp = distorionTemp;
        }

        public String getGlassTraTemp() {
            return glassTraTemp;
        }

        public void setGlassTraTemp(String glassTraTemp) {
            this.glassTraTemp = glassTraTemp;
        }

        public String getTensileStrength() {
            return tensileStrength;
        }

        public void setTensileStrength(String tensileStrength) {
            this.tensileStrength = tensileStrength;
        }

        public String getTensileModulus() {
            return tensileModulus;
        }

        public void setTensileModulus(String tensileModulus) {
            this.tensileModulus = tensileModulus;
        }

        public String getElongationAtBreak() {
            return elongationAtBreak;
        }

        public void setElongationAtBreak(String elongationAtBreak) {
            this.elongationAtBreak = elongationAtBreak;
        }

        public String getBendingStrength() {
            return bendingStrength;
        }

        public void setBendingStrength(String bendingStrength) {
            this.bendingStrength = bendingStrength;
        }

        public String getBendingModulus() {
            return bendingModulus;
        }

        public void setBendingModulus(String bendingModulus) {
            this.bendingModulus = bendingModulus;
        }

        public String getImpactStrength() {
            return impactStrength;
        }

        public void setImpactStrength(String impactStrength) {
            this.impactStrength = impactStrength;
        }

        public String getOthers() {
            return others;
        }

        public void setOthers(String others) {
            this.others = others;
        }

        public String getManuOrComp() {
            return manuOrComp;
        }

        public void setManuOrComp(String manuOrComp) {
            this.manuOrComp = manuOrComp;
        }
    }
}
