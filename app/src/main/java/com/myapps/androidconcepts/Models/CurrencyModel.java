package com.myapps.androidconcepts.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrencyModel {

    @SerializedName("success")
    @Expose
    private Boolean success;

    @SerializedName("terms")
    @Expose
    private String terms;

    @SerializedName("privacy")
    @Expose
    private String privacy;

    @SerializedName("timestamp")
    @Expose
    private Integer timestamp;

    @SerializedName("target")
    @Expose
    private String target;

    @SerializedName("rates")
    @Expose
    private Rates rates;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Rates getRates() {
        return rates;
    }

    public void setRates(Rates rates) {
        this.rates = rates;
    }

    public static class Rates {

        @SerializedName("ABC")
        @Expose
        private Double abc;
        @SerializedName("ACP")
        @Expose
        private Double acp;

        @SerializedName("ACT")
        @Expose
        private Double act;

        @SerializedName("ACT*")
        @Expose
        private Double act1;

        @SerializedName("ADA")
        @Expose
        private Double ada;
        @SerializedName("ADCN")
        @Expose
        private Double adcn;
        @SerializedName("ADL")
        @Expose
        private Double adl;
        @SerializedName("ADX")
        @Expose
        private Double adx;
        @SerializedName("ADZ")
        @Expose
        private Double adz;
        @SerializedName("AE")
        @Expose
        private Double ae;
        @SerializedName("AGI")
        @Expose
        private Integer agi;
        @SerializedName("AIB")
        @Expose
        private Double aib;
        @SerializedName("AIDOC")
        @Expose
        private Double aidoc;
        @SerializedName("AION")
        @Expose
        private Double aion;
        @SerializedName("AIR")
        @Expose
        private Double air;
        @SerializedName("ALT")
        @Expose
        private Double alt;
        @SerializedName("AMB")
        @Expose
        private Double amb;
        @SerializedName("AMM")
        @Expose
        private Double amm;
        @SerializedName("ANT")
        @Expose
        private Double ant;
        @SerializedName("APC")
        @Expose
        private Double apc;
        @SerializedName("APPC")
        @Expose
        private Double appc;
        @SerializedName("ARC")
        @Expose
        private Double arc;
        @SerializedName("ARCT")
        @Expose
        private Double arct;
        @SerializedName("ARDR")
        @Expose
        private Double ardr;
        @SerializedName("ARK")
        @Expose
        private Double ark;
        @SerializedName("ARN")
        @Expose
        private Double arn;
        @SerializedName("ASAFE2")
        @Expose
        private Double asafe2;
        @SerializedName("AST")
        @Expose
        private Double ast;
        @SerializedName("ATB")
        @Expose
        private Double atb;
        @SerializedName("ATM")
        @Expose
        private Double atm;
        @SerializedName("AURS")
        @Expose
        private Double aurs;
        @SerializedName("AVT")
        @Expose
        private Double avt;
        @SerializedName("BAR")
        @Expose
        private Double bar;
        @SerializedName("BASH")
        @Expose
        private Double bash;
        @SerializedName("BAT")
        @Expose
        private Double bat;
        @SerializedName("BAY")
        @Expose
        private Double bay;
        @SerializedName("BBP")
        @Expose
        private Double bbp;
        @SerializedName("BCD")
        @Expose
        private Double bcd;
        @SerializedName("BCH")
        @Expose
        private Double bch;
        @SerializedName("BCN")
        @Expose
        private Double bcn;
        @SerializedName("BCPT")
        @Expose
        private Double bcpt;
        @SerializedName("BEE")
        @Expose
        private Double bee;
        @SerializedName("BIO")
        @Expose
        private Double bio;
        @SerializedName("BLC")
        @Expose
        private Double blc;
        @SerializedName("BLOCK")
        @Expose
        private Double block;
        @SerializedName("BLU")
        @Expose
        private Double blu;
        @SerializedName("BLZ")
        @Expose
        private Double blz;
        @SerializedName("BMC")
        @Expose
        private Double bmc;
        @SerializedName("BNB")
        @Expose
        private Double bnb;
        @SerializedName("BNT")
        @Expose
        private Double bnt;
        @SerializedName("BOST")
        @Expose
        private Double bost;
        @SerializedName("BQ")
        @Expose
        private Double bq;
        @SerializedName("BQX")
        @Expose
        private Double bqx;
        @SerializedName("BRD")
        @Expose
        private Double brd;
        @SerializedName("BRIT")
        @Expose
        private Double brit;
        @SerializedName("BT1")
        @Expose
        private Integer bt1;
        @SerializedName("BT2")
        @Expose
        private Integer bt2;
        @SerializedName("BTC")
        @Expose
        private Double btc;
        @SerializedName("BTCA")
        @Expose
        private Double btca;
        @SerializedName("BTCS")
        @Expose
        private Double btcs;
        @SerializedName("BTCZ")
        @Expose
        private Double btcz;
        @SerializedName("BTG")
        @Expose
        private Double btg;
        @SerializedName("BTLC")
        @Expose
        private Integer btlc;
        @SerializedName("BTM")
        @Expose
        private Double btm;
        @SerializedName("BTM1")
        @Expose
        private Double btm1;
        @SerializedName("BTQ")
        @Expose
        private Double btq;
        @SerializedName("BTS")
        @Expose
        private Double bts;
        @SerializedName("BTX")
        @Expose
        private Double btx;
        @SerializedName("BURST")
        @Expose
        private Double burst;


        public Double getAbc() {
            return abc;
        }

        public void setAbc(Double abc) {
            this.abc = abc;
        }

        public Double getAcp() {
            return acp;
        }

        public void setAcp(Double acp) {
            this.acp = acp;
        }

        public Double getAct() {
            return act;
        }

        public void setAct(Double act) {
            this.act = act;
        }

        public Double getAct1() {
            return act1;
        }

        public void setAct1(Double act1) {
            this.act1 = act1;
        }

        public Double getAda() {
            return ada;
        }

        public void setAda(Double ada) {
            this.ada = ada;
        }

        public Double getAdcn() {
            return adcn;
        }

        public void setAdcn(Double adcn) {
            this.adcn = adcn;
        }

        public Double getAdl() {
            return adl;
        }

        public void setAdl(Double adl) {
            this.adl = adl;
        }

        public Double getAdx() {
            return adx;
        }

        public void setAdx(Double adx) {
            this.adx = adx;
        }

        public Double getAdz() {
            return adz;
        }

        public void setAdz(Double adz) {
            this.adz = adz;
        }

        public Double getAe() {
            return ae;
        }

        public void setAe(Double ae) {
            this.ae = ae;
        }

        public Integer getAgi() {
            return agi;
        }

        public void setAgi(Integer agi) {
            this.agi = agi;
        }

        public Double getAib() {
            return aib;
        }

        public void setAib(Double aib) {
            this.aib = aib;
        }

        public Double getAidoc() {
            return aidoc;
        }

        public void setAidoc(Double aidoc) {
            this.aidoc = aidoc;
        }

        public Double getAion() {
            return aion;
        }

        public void setAion(Double aion) {
            this.aion = aion;
        }

        public Double getAir() {
            return air;
        }

        public void setAir(Double air) {
            this.air = air;
        }

        public Double getAlt() {
            return alt;
        }

        public void setAlt(Double alt) {
            this.alt = alt;
        }

        public Double getAmb() {
            return amb;
        }

        public void setAmb(Double amb) {
            this.amb = amb;
        }

        public Double getAmm() {
            return amm;
        }

        public void setAmm(Double amm) {
            this.amm = amm;
        }

        public Double getAnt() {
            return ant;
        }

        public void setAnt(Double ant) {
            this.ant = ant;
        }

        public Double getApc() {
            return apc;
        }

        public void setApc(Double apc) {
            this.apc = apc;
        }

        public Double getAppc() {
            return appc;
        }

        public void setAppc(Double appc) {
            this.appc = appc;
        }

        public Double getArc() {
            return arc;
        }

        public void setArc(Double arc) {
            this.arc = arc;
        }

        public Double getArct() {
            return arct;
        }

        public void setArct(Double arct) {
            this.arct = arct;
        }

        public Double getArdr() {
            return ardr;
        }

        public void setArdr(Double ardr) {
            this.ardr = ardr;
        }

        public Double getArk() {
            return ark;
        }

        public void setArk(Double ark) {
            this.ark = ark;
        }

        public Double getArn() {
            return arn;
        }

        public void setArn(Double arn) {
            this.arn = arn;
        }

        public Double getAsafe2() {
            return asafe2;
        }

        public void setAsafe2(Double asafe2) {
            this.asafe2 = asafe2;
        }

        public Double getAst() {
            return ast;
        }

        public void setAst(Double ast) {
            this.ast = ast;
        }

        public Double getAtb() {
            return atb;
        }

        public void setAtb(Double atb) {
            this.atb = atb;
        }

        public Double getAtm() {
            return atm;
        }

        public void setAtm(Double atm) {
            this.atm = atm;
        }

        public Double getAurs() {
            return aurs;
        }

        public void setAurs(Double aurs) {
            this.aurs = aurs;
        }

        public Double getAvt() {
            return avt;
        }

        public void setAvt(Double avt) {
            this.avt = avt;
        }

        public Double getBar() {
            return bar;
        }

        public void setBar(Double bar) {
            this.bar = bar;
        }

        public Double getBash() {
            return bash;
        }

        public void setBash(Double bash) {
            this.bash = bash;
        }

        public Double getBat() {
            return bat;
        }

        public void setBat(Double bat) {
            this.bat = bat;
        }

        public Double getBay() {
            return bay;
        }

        public void setBay(Double bay) {
            this.bay = bay;
        }

        public Double getBbp() {
            return bbp;
        }

        public void setBbp(Double bbp) {
            this.bbp = bbp;
        }

        public Double getBcd() {
            return bcd;
        }

        public void setBcd(Double bcd) {
            this.bcd = bcd;
        }

        public Double getBch() {
            return bch;
        }

        public void setBch(Double bch) {
            this.bch = bch;
        }

        public Double getBcn() {
            return bcn;
        }

        public void setBcn(Double bcn) {
            this.bcn = bcn;
        }

        public Double getBcpt() {
            return bcpt;
        }

        public void setBcpt(Double bcpt) {
            this.bcpt = bcpt;
        }

        public Double getBee() {
            return bee;
        }

        public void setBee(Double bee) {
            this.bee = bee;
        }

        public Double getBio() {
            return bio;
        }

        public void setBio(Double bio) {
            this.bio = bio;
        }

        public Double getBlc() {
            return blc;
        }

        public void setBlc(Double blc) {
            this.blc = blc;
        }

        public Double getBlock() {
            return block;
        }

        public void setBlock(Double block) {
            this.block = block;
        }

        public Double getBlu() {
            return blu;
        }

        public void setBlu(Double blu) {
            this.blu = blu;
        }

        public Double getBlz() {
            return blz;
        }

        public void setBlz(Double blz) {
            this.blz = blz;
        }

        public Double getBmc() {
            return bmc;
        }

        public void setBmc(Double bmc) {
            this.bmc = bmc;
        }

        public Double getBnb() {
            return bnb;
        }

        public void setBnb(Double bnb) {
            this.bnb = bnb;
        }

        public Double getBnt() {
            return bnt;
        }

        public void setBnt(Double bnt) {
            this.bnt = bnt;
        }

        public Double getBost() {
            return bost;
        }

        public void setBost(Double bost) {
            this.bost = bost;
        }

        public Double getBq() {
            return bq;
        }

        public void setBq(Double bq) {
            this.bq = bq;
        }

        public Double getBqx() {
            return bqx;
        }

        public void setBqx(Double bqx) {
            this.bqx = bqx;
        }

        public Double getBrd() {
            return brd;
        }

        public void setBrd(Double brd) {
            this.brd = brd;
        }

        public Double getBrit() {
            return brit;
        }

        public void setBrit(Double brit) {
            this.brit = brit;
        }

        public Integer getBt1() {
            return bt1;
        }

        public void setBt1(Integer bt1) {
            this.bt1 = bt1;
        }

        public Integer getBt2() {
            return bt2;
        }

        public void setBt2(Integer bt2) {
            this.bt2 = bt2;
        }

        public Double getBtc() {
            return btc;
        }

        public void setBtc(Double btc) {
            this.btc = btc;
        }

        public Double getBtca() {
            return btca;
        }

        public void setBtca(Double btca) {
            this.btca = btca;
        }

        public Double getBtcs() {
            return btcs;
        }

        public void setBtcs(Double btcs) {
            this.btcs = btcs;
        }

        public Double getBtcz() {
            return btcz;
        }

        public void setBtcz(Double btcz) {
            this.btcz = btcz;
        }

        public Double getBtg() {
            return btg;
        }

        public void setBtg(Double btg) {
            this.btg = btg;
        }

        public Integer getBtlc() {
            return btlc;
        }

        public void setBtlc(Integer btlc) {
            this.btlc = btlc;
        }

        public Double getBtm() {
            return btm;
        }

        public void setBtm(Double btm) {
            this.btm = btm;
        }

        public Double getBtm1() {
            return btm1;
        }

        public void setBtm1(Double btm1) {
            this.btm1 = btm1;
        }

        public Double getBtq() {
            return btq;
        }

        public void setBtq(Double btq) {
            this.btq = btq;
        }

        public Double getBts() {
            return bts;
        }

        public void setBts(Double bts) {
            this.bts = bts;
        }

        public Double getBtx() {
            return btx;
        }

        public void setBtx(Double btx) {
            this.btx = btx;
        }

        public Double getBurst() {
            return burst;
        }

        public void setBurst(Double burst) {
            this.burst = burst;
        }

    }
}
