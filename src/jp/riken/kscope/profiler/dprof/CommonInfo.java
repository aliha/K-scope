/*
 * K-scope
 * Copyright 2012-2013 RIKEN, Japan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jp.riken.kscope.profiler.dprof;

import jp.riken.kscope.profiler.common.PaDiscrimInfo;
import jp.riken.kscope.profiler.common.ProfConstant;

/**
 * 共通情報を保持する
 *
 * @author RIKEN
 */
public class CommonInfo {

    private int processNum;
    private int measureOption;
    private short execKind;
    private short threadNum;
    private int cpuClock;
    private String measureTimeInfo;
    private int recomMemory;
    private float sampInterval;
    private int logicDimention;
    private int logicShapeX;
    private int logicShapeY;
    private int logicShapeZ;
    private int logicCordinateX;
    private int logicCordinateY;
    private int logicCordinateZ;
    private int phisShapeX;
    private int phisShapeY;
    private int phisShapeZ;
    private int phisShapeA;
    private int phisShapeB;
    private int phisShapeC;
    private int phisCordinateX;
    private int phisCordinateY;
    private int phisCordinateZ;
    private int phisCordinateA;
    private int phisCordinateB;
    private int phisCordinateC;
    private String paEventVal;
    private PaDiscrimInfo paDiscrimInfo;

    /**
     * 測定時オプションの"COLL_OPT_COM"を取得する
     *
     * @return COLL_OPT_COMが設定されている:true 設定されていない:false
     */
    public boolean isOptCom() {
        return ((measureOption & ProfConstant.DPRF_COLL_OPT_COM) != 0);
    }

    /**
     * 測定時オプションの"COLL_OPT_PA"を取得する
     *
     * @return COLL_OPT_PAが設定されている:true 設定されていない:false
     */
    public boolean isOptPa() {
        return ((measureOption & ProfConstant.DPRF_COLL_OPT_PA) != 0);
    }

    /**
     * 測定時オプションの"COLL_OPT_SAMPLING"を取得する
     *
     * @return COLL_OPT_SAMPLINGが設定されている:true 設定されていない:false
     */
    public boolean isOptSampling() {
        return ((measureOption & ProfConstant.DPRF_COLL_OPT_SAMPLING) != 0);
    }

    /**
     * 測定時オプションの"COLL_OPT_REALTIME"を取得する
     *
     * @return COLL_OPT_REALTIMEが設定されている:true 設定されていない:false
     */
    public boolean isOptRealTime() {
        return ((measureOption & ProfConstant.DPRF_COLL_OPT_REALTIME) != 0);
    }

    /**
     * 測定時オプションの"COLL_OPT_PA_RANGE"を取得する
     *
     * @return COLL_OPT_PA_RANGEが設定されている:true 設定されていない:false
     */
    public boolean isOptPARange() {
        return ((measureOption & ProfConstant.DPRF_COLL_OPT_PA_RANGE) != 0);
    }

    /**
     * 測定時オプションの"COLL_OPT_PA_EVENT"を取得する
     *
     * @return COLL_OPT_PA_EVENTが設定されている:true 設定されていない:false
     */
    public boolean isOptPAEvent() {
        return ((measureOption & ProfConstant.DPRF_COLL_OPT_PA_EVENT) != 0);
    }

    /**
     * 測定時オプションの"COLL_OPT_CALLGRAPH"を取得する
     *
     * @return COLL_OPT_CALLGRAPHが設定されている:true 設定されていない:false
     */
    public boolean isOptCallGraph() {
        return ((measureOption & ProfConstant.DPRF_COLL_OPT_CALLGRAPH) != 0);
    }

    /**
     * 測定時オプションの"COLL_OPT_MPIELAPS"を取得する
     *
     * @return COLL_OPT_MPIELAPSが設定されている:true 設定されていない:false
     */
    public boolean isOptMPIElaps() {
        return ((measureOption & ProfConstant.DPRF_COLL_OPT_MPIELAPS) != 0);
    }

    /**
     * 測定時オプションの"COLL_SAMP_RANGE"を取得する
     *
     * @return COLL_SAMP_RANGEが設定されている:true 設定されていない:false
     */
    public boolean isOptSampRange() {
        return ((measureOption & ProfConstant.DPRF_COLL_OPT_SAMP_RANGE) != 0);
    }

    /**
     * 測定時オプションの"COLL_USERFUNC"を取得する
     *
     * @return COLL_OPT_USERFUNCが設定されている:true 設定されていない:false
     */
    public boolean isOptUserFunc() {
        return ((measureOption & ProfConstant.DPRF_COLL_OPT_USERFUNC) != 0);
    }

    /**
     * 測定時オプションの"COLL_OPT_SAMP_RANGE_COST"を取得する
     *
     * @return COLL_OPT_SAMP_RANGE_COSTが設定されている:true 設定されていない:false
     */
    public boolean isOptSampRangeCost() {
        return ((measureOption & ProfConstant.DPRF_COLL_OPT_SAMP_RANGE_COST) != 0);
    }

    /**
     * 測定時オプションの"COLL_OPT_ST_COM"を取得する
     *
     * @return COLL_OPT_ST_COMが設定されている:true 設定されていない:false
     */
    public boolean isOptStCom() {
        return ((measureOption & ProfConstant.DPRF_COLL_OPT_ST_COM) != 0);
    }

    /**
     * 測定時オプションの"COLL_OPT_SLEEP"を取得する
     *
     * @return COLL_OPT_SLEEPが設定されている:true 設定されていない:false
     */
    public boolean isOptSleep() {
        return ((measureOption & ProfConstant.DPRF_COLL_OPT_SLEEP) != 0);
    }

    /**
     * 実行形態の"EXEC_KIND_SERIAL"を取得する
     *
     * @return EXEC_KIND_SERIALが設定されている:true 設定されていない:false
     */
    public boolean isExecSerial() {
        return ((execKind & ProfConstant.EXEC_KIND_SERIAL) != 0);
    }

    /**
     * 実行形態の"EXEC_KIND_MPI"を取得する
     *
     * @return EXEC_KIND_MPIが設定されている:true 設定されていない:false
     */
    public boolean isExecMPI() {
        return ((execKind & ProfConstant.EXEC_KIND_MPI) != 0);
    }

    /**
     * 実行形態の"EXEC_KIND_XPF"を取得する
     *
     * @return EXEC_KIND_XPFが設定されている:true 設定されていない:false
     */
    public boolean isExecXPF() {
        return ((execKind & ProfConstant.EXEC_KIND_XPF) != 0);
    }

    /**
     * 実行形態の"EXEC_KIND_FULL"を取得する
     *
     * @return EXEC_KIND_FULLが設定されている:true 設定されていない:false
     */
    public boolean isExecFull() {
        return ((execKind & ProfConstant.EXEC_KIND_FULL) != 0);
    }

    /**
     * 実行形態の"EXEC_KIND_LIMITED"を取得する
     *
     * @return EXEC_KIND_LIMITEDが設定されている:true 設定されていない:false
     */
    public boolean isExecLimited() {
        return ((execKind & ProfConstant.EXEC_KIND_LIMITED) != 0);
    }

    /**
     * 実行形態の"EXEC_KIND_AUTO"を取得する
     *
     * @return EXEC_KIND_AUTOが設定されている:true 設定されていない:false
     */
    public boolean isExecAuto() {
        return ((execKind & ProfConstant.EXEC_KIND_AUTO) != 0);
    }

    /**
     * 実行形態の"EXEC_KIND_OMP"を取得する
     *
     * @return EXEC_KIND_OMPが設定されている:true 設定されていない:false
     */
    public boolean isExecOMP() {
        return ((execKind & ProfConstant.EXEC_KIND_OMP) != 0);
    }

    /**
     * 実行形態の"EXEC_KIND_UNKNOWN"を取得する
     *
     * @return EXEC_KIND_UNKNOWNが設定されている:true 設定されていない:false
     */
    public boolean isExecUnknown() {
        return (execKind == ProfConstant.EXEC_KIND_UNKNOWN);
    }

    /**
     * プロセス数を取得する
     * @return プロセス数
     */
    public int getProcessNum() {
        return processNum;
    }

    /**
     * 測定時オプションを取得する
     * @return 測定時オプション
     */
    public int getMeasureOption() {
        return measureOption;
    }

    /**
     * 実行形態を取得する
     * @return 実行形態
     */
    public short getRunStyle() {
        return execKind;
    }

    /**
     * スレッド数を取得する
     * @return スレッド数
     */
    public short getThreadNum() {
        return threadNum;
    }

    /**
     * CPUクロック周波数を取得する
     * @return CPUクロック周波数
     */
    public int getCpuClock() {
        return cpuClock;
    }

    /**
     * 測定時間情報を取得する
     * @return 測定時間情報
     */
    public String getMeasureTimeInfo() {
        return measureTimeInfo;
    }

    /**
     * メモリー推奨値を取得する
     * @return メモリー推奨値
     */
    public int getRecomMemory() {
        return recomMemory;
    }

    /**
     * サンプリング間隔を取得する
     * @return サンプリング間隔
     */
    public float getSampInterval() {
        return sampInterval;
    }

    /**
     * 論理次元数を取得する
     * @return 論理次元数
     */
    public int getLogicDimention() {
        return logicDimention;
    }

    /**
     * 論理形状-Xを取得する
     * @return 論理形状-X
     */
    public int getLogicShapeX() {
        return logicShapeX;
    }

    /**
     * 論理形状-Yを取得する
     * @return 論理形状-Y
     */
    public int getLogicShapeY() {
        return logicShapeY;
    }

    /**
     * 論理形状-Zを取得する
     * @return 論理形状-Z
     */
    public int getLogicShapeZ() {
        return logicShapeZ;
    }

    /**
     * 論理座標-Xを取得する
     * @return 論理座標-X
     */
    public int getLogicCordinateX() {
        return logicCordinateX;
    }

    /**
     * 論理座標-Yを取得する
     * @return 論理座標-Y
     */
    public int getLogicCordinateY() {
        return logicCordinateY;
    }

    /**
     * 論理座標-Zを取得する
     * @return 論理座標-Z
     */
    public int getLogicCordinateZ() {
        return logicCordinateZ;
    }

    /**
     * 物理形状-Xを取得する
     * @return 物理形状-X
     */
    public int getPhisShapeX() {
        return phisShapeX;
    }

    /**
     * 物理形状-Yを取得する
     * @return 物理形状-Y
     */
    public int getPhisShapeY() {
        return phisShapeY;
    }

    /**
     * 物理形状-Zを取得する
     * @return 物理形状-Z
     */
    public int getPhisShapeZ() {
        return phisShapeZ;
    }

    /**
     * 物理形状-Aを取得する
     * @return 物理形状-A
     */
    public int getPhisShapeA() {
        return phisShapeA;
    }

    /**
     * 物理形状-Bを取得する
     * @return 物理形状-B
     */
    public int getPhisShapeB() {
        return phisShapeB;
    }

    /**
     * 物理形状-Cを取得する
     * @return 物理形状-C
     */
    public int getPhisShapeC() {
        return phisShapeC;
    }

    /**
     * 物理座標-Xを取得する
     * @return 物理座標-X
     */
    public int getPhisCordinateX() {
        return phisCordinateX;
    }

    /**
     * 物理座標-Yを取得する
     * @return 物理座標-Y
     */
    public int getPhisCordinateY() {
        return phisCordinateY;
    }

    /**
     * 物理座標-Zを取得する
     * @return 物理座標-Z
     */
    public int getPhisCordinateZ() {
        return phisCordinateZ;
    }

    /**
     * 物理座標-Aを取得する
     * @return 物理座標-A
     */
    public int getPhisCordinateA() {
        return phisCordinateA;
    }

    /**
     * 物理座標-Bを取得する
     * @return 物理座標-B
     */
    public int getPhisCordinateB() {
        return phisCordinateB;
    }

    /**
     * 物理座標-Cを取得する
     * @return 物理座標-C
     */
    public int getPhisCordinateC() {
        return phisCordinateC;
    }

    /**
     * PA識別情報を取得する
     * @return PA識別情報
     */
    public PaDiscrimInfo getPaDiscrimInfo() {
        return paDiscrimInfo;
    }

    /**
     * PAイベント指定値を取得する
     * @return PAイベント指定値
     */
    public String getPaEventVal() {
        return paEventVal;
    }

    /**
     * プロセス数を設定する
     * @param processNum
     *            設定するプロセス数
     */
    public void setProcessNum(int processNum) {
        this.processNum = processNum;
    }

    /**
     * 測定時オプションを設定する
     * @param measureOption
     *            設定する測定時オプション
     */
    public void setMeasureOption(int measureOption) {
        this.measureOption = measureOption;
    }

    /**
     * 実行形態を設定する
     * @param runStyle
     *            設定する実行形態
     */
    public void setRunStyle(short runStyle) {
        this.execKind = runStyle;
    }

    /**
     * スレッド数を設定する
     * @param threadNum
     *            設定するスレッド数
     */
    public void setThreadNum(short threadNum) {
        this.threadNum = threadNum;
    }

    /**
     * CPUクロック周波数を設定する
     * @param cpuClock
     *            設定するCPUクロック周波数
     */
    public void setCpuClock(int cpuClock) {
        this.cpuClock = cpuClock;
    }

    /**
     * 測定時間情報を設定する
     * @param measureTimeInfo
     *            設定する測定時間情報
     */
    public void setMeasureTimeInfo(String measureTimeInfo) {
        this.measureTimeInfo = measureTimeInfo;
    }

    /**
     * メモリー推奨値を設定する
     * @param recomMemory
     *            設定するメモリー推奨値
     */
    public void setRecomMemory(int recomMemory) {
        this.recomMemory = recomMemory;
    }

    /**
     * サンプリング間隔を設定する
     * @param sampInterval
     *            設定するサンプリング間隔
     */
    public void setSampInterval(float sampInterval) {
        this.sampInterval = sampInterval;
    }

    /**
     * 論理次元数を設定する
     * @param logicDimention
     *            設定する論理次元数
     */
    public void setLogicDimention(int logicDimention) {
        this.logicDimention = logicDimention;
    }

    /**
     * 論理形状-Xを設定する
     * @param logicShapeX
     *            設定する論理形状-X
     */
    public void setLogicShapeX(int logicShapeX) {
        this.logicShapeX = logicShapeX;
    }

    /**
     * 論理形状-Yを設定する
     * @param logicShapeY
     *            設定する論理形状-Y
     */
    public void setLogicShapeY(int logicShapeY) {
        this.logicShapeY = logicShapeY;
    }

    /**
     * 論理形状-Zを設定する
     * @param logicShapeZ
     *            設定する論理形状-Z
     */
    public void setLogicShapeZ(int logicShapeZ) {
        this.logicShapeZ = logicShapeZ;
    }

    /**
     * 論理座標-Xを設定する
     * @param logicCordinateX
     *            設定する論理座標-X
     */
    public void setLogicCordinateX(int logicCordinateX) {
        this.logicCordinateX = logicCordinateX;
    }

    /**
     * 論理座標-Yを設定する
     * @param logicCordinateY
     *            設定する論理座標-Y
     */
    public void setLogicCordinateY(int logicCordinateY) {
        this.logicCordinateY = logicCordinateY;
    }

    /**
     * 論理座標-Zを設定する
     * @param logicCordinateZ
     *            設定する論理座標-Z
     */
    public void setLogicCordinateZ(int logicCordinateZ) {
        this.logicCordinateZ = logicCordinateZ;
    }

    /**
     * 物理形状-Xを設定する
     * @param phisShapeX
     *            設定する物理形状-X
     */
    public void setPhisShapeX(int phisShapeX) {
        this.phisShapeX = phisShapeX;
    }

    /**
     * 物理形状-Yを設定する
     * @param phisShapeY
     *            設定する物理形状-Y
     */
    public void setPhisShapeY(int phisShapeY) {
        this.phisShapeY = phisShapeY;
    }

    /**
     * 物理形状-Zを設定する
     * @param phisShapeZ
     *            設定する物理形状-Z
     */
    public void setPhisShapeZ(int phisShapeZ) {
        this.phisShapeZ = phisShapeZ;
    }

    /**
     * 物理形状-Aを設定する
     * @param phisShapeA
     *            設定する物理形状-A
     */
    public void setPhisShapeA(int phisShapeA) {
        this.phisShapeA = phisShapeA;
    }

    /**
     * 物理形状-Bを設定する
     * @param phisShapeB
     *            設定する物理形状-B
     */
    public void setPhisShapeB(int phisShapeB) {
        this.phisShapeB = phisShapeB;
    }

    /**
     * 物理形状-Cを設定する
     * @param phisShapeC
     *            設定する物理形状-C
     */
    public void setPhisShapeC(int phisShapeC) {
        this.phisShapeC = phisShapeC;
    }

    /**
     * 物理座標-Xを設定する
     * @param phisCordinateX
     *            設定する物理座標-X
     */
    public void setPhisCordinateX(int phisCordinateX) {
        this.phisCordinateX = phisCordinateX;
    }

    /**
     * 物理座標-Yを設定する
     * @param phisCordinateY
     *            設定する物理座標-Y
     */
    public void setPhisCordinateY(int phisCordinateY) {
        this.phisCordinateY = phisCordinateY;
    }

    /**
     * 物理座標-Zを設定する
     * @param phisCordinateZ
     *            設定する物理座標-Z
     */
    public void setPhisCordinateZ(int phisCordinateZ) {
        this.phisCordinateZ = phisCordinateZ;
    }

    /**
     * 物理座標-Aを設定する
     * @param phisCordinateA
     *            設定する物理座標-A
     */
    public void setPhisCordinateA(int phisCordinateA) {
        this.phisCordinateA = phisCordinateA;
    }

    /**
     * 物理座標-Bを設定する
     * @param phisCordinateB
     *            設定する物理座標-B
     */
    public void setPhisCordinateB(int phisCordinateB) {
        this.phisCordinateB = phisCordinateB;
    }

    /**
     * 物理座標-Cを設定する
     * @param phisCordinateC
     *            設定する物理座標-C
     */
    public void setPhisCordinateC(int phisCordinateC) {
        this.phisCordinateC = phisCordinateC;
    }

    /**
     * PA識別情報を設定する
     * @param paDiscrimInfo
     *            設定するPA識別情報
     */
    public void setPaDiscrimInfo(PaDiscrimInfo paDiscrimInfo) {
        this.paDiscrimInfo = paDiscrimInfo;
    }

    /**
     * PAイベント指定値を設定する
     * @param paEventVal
     *            設定するPAイベント指定値
     */
    public void setPaEventVal(String paEventVal) {
        this.paEventVal = paEventVal;
    }

}
