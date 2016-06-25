package org.senml.data;

/**
+---------------+------+---------+
|         SenML | JSON | Type    |
+---------------+------+---------+
|     Base Name | bn   | String  |
|     Base Time | bt   | Number  |
|     Base Unit | bu   | String  |
|    Base Value | bv   | Number  |
|       Version | bver | Number  |
|          Name | n    | String  |
|          Unit | u    | String  |
|         Value | v    | Number  |
|  String Value | vs   | String  |
| Boolean Value | vb   | Boolean |
|    Data Value | vd   | String  |
|     Value Sum | s    | Number  |
|          Time | t    | Number  |
|   Update Time | ut   | Number  |
+---------------+------+---------+
*/
import java.lang.Number;

public class SenML {

	String bn;

	Number bt;

	String bu;

	Number bv, bver;

	String n, u;

	Number v;

	String vs;

	Boolean vb;

	String vd;

	Number s, t, ut;

	public String getBaseName() {
		return bn;
	}

	public void setBaseName(String bn) {
		this.bn = bn;
	}

	public Number getBaseTime() {
		return bt;
	}

	public void setBaseTime(Number bt) {
		this.bt = bt;
	}

	public String getBaseUnit() {
		return bu;
	}

	public void setBaseUnit(String bu) {
		this.bu = bu;
	}

	public Number getBaseValue() {
		return bv;
	}

	public void setBaseValue(Number bv) {
		this.bv = bv;
	}

	public Number getVersion() {
		return bver;
	}

	public void setVersion(Number bver) {
		this.bver = bver;
	}

	public String getName() {
		return n;
	}

	public void setName(String n) {
		this.n = n;
	}

	public String getUnit() {
		return u;
	}

	public void setUnit(String u) {
		this.u = u;
	}

	public Number getValue() {
		return v;
	}

	public void setValue(Number v) {
		this.v = v;
	}

	public String getStringValue() {
		return vs;
	}

	public void setStringValue(String vs) {
		this.vs = vs;
	}

	public Boolean getBooleanValue() {
		return vb;
	}

	public void setBooleanValue(Boolean vb) {
		this.vb = vb;
	}

	public String getDataValue() {
		return vd;
	}

	public void setDataValue(String vd) {
		this.vd = vd;
	}

	public Number getValueSum() {
		return s;
	}

	public void setValueSum(Number s) {
		this.s = s;
	}

	public Number getTime() {
		return t;
	}

	public void setTime(Number t) {
		this.t = t;
	}

	public Number getUpdateTime() {
		return ut;
	}

	public void setUpdateTime(Number ut) {
		this.ut = ut;
	}

	@Override
	public String toString() {
		return "SenML [ " + (bn != null ? "bn=" + bn + "  " : "") + (bt != null ? "bt=" + bt + "  " : "")
				+ (bu != null ? "bu=" + bu + "  " : "") + (bv != null ? "bv=" + bv + "  " : "")
				+ (bver != null ? "bver=" + bver + "  " : "") + (n != null ? "n=" + n + "  " : "")
				+ (u != null ? "u=" + u + "  " : "") + (v != null ? "v=" + v + "  " : "")
				+ (vs != null ? "vs=" + vs + "  " : "") + (vb != null ? "vb=" + vb + "  " : "")
				+ (vd != null ? "vd=" + vd + "  " : "") + (s != null ? "s=" + s + "  " : "")
				+ (t != null ? "t=" + t + "  " : "") + (ut != null ? "ut=" + ut + "  " : "") + "]";
	}

}
