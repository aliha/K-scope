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
package jp.riken.kscope.profiler.eprof;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.riken.kscope.Message;
import jp.riken.kscope.common.PROFILERINFO_TYPE;
import jp.riken.kscope.profiler.IProfilerReader;
import jp.riken.kscope.profiler.ProfilerDprofData;
import jp.riken.kscope.profiler.ProfilerEprofData;
import jp.riken.kscope.profiler.common.BaseReader;
import jp.riken.kscope.profiler.common.MagicKey;
import jp.riken.kscope.profiler.common.PaDiscrimInfo;

/**
 * EProfileファイルを読み込み、情報を保持する
 *
 * @author RIKEN
 *
 */
public class EProfReader extends BaseReader implements IProfilerReader {

    private final String FILE_ID_EPROF = "EPRF"; // EProfファイルを表すファイル識別文字
    private final int FILE_ID_LENGTH = 4; // ファイル識別文字の長さ
    private final int MEASURE_TIME_LENGTH = 32; // 測定時間情報文字列の長さ
    private final short PROFILER_VERSION = 0x402;
    /** ハードウェアモニタ情報（ＰＡ情報）テーブル:Cacheのテーブル */
    private final String PA_EVENT_CACHE = "Cache";
    /** ハードウェアモニタ情報（ＰＡ情報）テーブル:Instructionsのテーブル */
    private final String PA_EVENT_INSTRUCTIONS = "Instructions";
    /** ハードウェアモニタ情報（ＰＡ情報）テーブル:MEM_accessのテーブル */
    private final String PA_EVENT_MEM_ACCESS = "MEM_access";
    /** ハードウェアモニタ情報（ＰＡ情報）テーブル:Performanceのテーブル */
    private final String PA_EVENT_PERFORMANCE = "Performance";
    /** ハードウェアモニタ情報（ＰＡ情報）テーブル:Statisticsのテーブル */
    private final String PA_EVENT_STATISTICS = "Statistics";

    /* PAイベント指定値ごとのPA情報テーブルの大きさ */
    private final Map<String, Integer> MAP_PA_INFO_LENGTH = new HashMap<String, Integer>() {
        private static final long serialVersionUID = 1L;
        {
            put(PA_EVENT_CACHE, 10);
            put(PA_EVENT_INSTRUCTIONS, 9);
            put(PA_EVENT_MEM_ACCESS, 10);
            put(PA_EVENT_PERFORMANCE, 10);
            put(PA_EVENT_STATISTICS, 10);
        }
    };

    /**マジックキー */
    private MagicKey magicKey;
    /** 共通情報 */
    private CommonInfo commonInfo;
    /** イベントカウンタ情報 */
    private EventCounterInfo eventInfo;
    /** 読み込み時のエンディアン設定 */
    private int endian;
    /** 読込プロファイラファイル */
    private File profFile;

    /**
     * 指定されたプロファイラファイルの情報を読み込む
     *
     * @param fEProf
     *            読み込むプロファイラファイル
     * @param endian
     *            エンディアン設定　LITTLE_ENDIAN:0x00 BIG_ENDIAN:0x01;
     * @throws IOException		ファイル読込エラー
     */
    @Override
    public void readFile(File fEProf, int endian) throws Exception {
        // エンディアンを設定
        this.endian = endian;
        this.profFile = fEProf;

        long fileSize = fEProf.length();
        ByteBuffer byteBuf = ByteBuffer.allocate((int) fileSize);
        FileInputStream fis = new FileInputStream(fEProf);

        while (fis.available() > 0) {
            byteBuf.put((byte) fis.read());
        }
        byteBuf.flip();

        magicKey = readMagicKey(byteBuf);
        commonInfo = readCommonInfo(byteBuf);
        eventInfo = readEventCounterInfo(byteBuf);

        fis.close();
    }

    /**
     * プロファイラファイルから読み込まれたマジックキー情報のインスタンスを返す。readProfile(File)が実行されていない場合、nullを返す
     *
     * @return
     *         マジックキー情報を格納したMagicKeyクラスのインスタンス。ただし、readProfile(File)が実行されていない場合はnull
     */
    public MagicKey getMagicKey() {
        return magicKey;
    }

    /**
     * プロファイラファイルから読み込まれた共通情報のインスタンスを返す。readProfile(File)が実行されていない場合、nullを返す
     *
     * @return
     *         共通情報を格納したCommonInfoクラスのインスタンス。ただし、readProfile(File)が実行されていない場合はnull
     */
    public CommonInfo getCommonInfo() {
        return commonInfo;
    }

    /**
     * プロファイラファイルから読み込まれたスレッド情報のインスタンスのリストを返す。readProfile(File)が実行されていない場合、
     * nullを返す
     *
     * @return スレッド情報を格納したThreadInfoクラスのインスタンスのリスト。ただし、readProfile(File)
     *         が実行されていない場合はnull
     */
    public EventCounterInfo getEventCounterInfo() {
        return this.eventInfo;
    }


    /*マジックキー情報の読み込み*/
    private MagicKey readMagicKey(ByteBuffer byteBuf) throws Exception {
        MagicKey newMagicKey = new MagicKey();
        String fileID = getString(byteBuf, FILE_ID_LENGTH);

        if (!FILE_ID_EPROF.equals(fileID)) {
        	throw new Exception(Message.getString("dialog.common.error") + //エラー
        			": " +
        			Message.getString("eprofreader.exception.notvalid")); //有効なEProfファイルではありません。
        }
        newMagicKey.setId(fileID);
        newMagicKey.setAdd_mode(getShort(byteBuf));
        short version = getShort(byteBuf);
        if (version != PROFILER_VERSION) {
        	throw new Exception(Message.getString("dialog.common.error") + //エラー
        			": " +
        			Message.getString("eprofreader.exception.outside", version, PROFILER_VERSION)); //サポート対象外のEProfバージョンです。 読込=%#04X サポート=%#04X
        }
        newMagicKey.setVer(version);
        return newMagicKey;
    }

    /**
     * 共通情報の読み込み
     * @param byteBuf			ファイルバイトバッファ
     * @return		共通情報
     */
    private CommonInfo readCommonInfo(ByteBuffer byteBuf) {
        CommonInfo newCommonInfo = new CommonInfo();
        newCommonInfo.setProcessNum(getInt(byteBuf));
        newCommonInfo.setMeasureOption(getInt(byteBuf));
        newCommonInfo.setRunStyle(getShort(byteBuf));
        newCommonInfo.setThreadNum(getShort(byteBuf));
        newCommonInfo.setCpuClock(getInt(byteBuf));
        newCommonInfo.setMeasureTimeInfo(getString(byteBuf, MEASURE_TIME_LENGTH));
        newCommonInfo.setLogicDimention(getInt(byteBuf));
        newCommonInfo.setLogicShapeX(getInt(byteBuf));
        newCommonInfo.setLogicShapeY(getInt(byteBuf));
        newCommonInfo.setLogicShapeZ(getInt(byteBuf));
        newCommonInfo.setLogicCordinateX(getInt(byteBuf));
        newCommonInfo.setLogicCordinateY(getInt(byteBuf));
        newCommonInfo.setLogicCordinateZ(getInt(byteBuf));
        newCommonInfo.setPhisShapeX(getInt(byteBuf));
        newCommonInfo.setPhisShapeY(getInt(byteBuf));
        newCommonInfo.setPhisShapeZ(getInt(byteBuf));
        newCommonInfo.setPhisShapeA(getInt(byteBuf));
        newCommonInfo.setPhisShapeB(getInt(byteBuf));
        newCommonInfo.setPhisShapeC(getInt(byteBuf));
        newCommonInfo.setPhisCordinateX(getInt(byteBuf));
        newCommonInfo.setPhisCordinateY(getInt(byteBuf));
        newCommonInfo.setPhisCordinateZ(getInt(byteBuf));
        newCommonInfo.setPhisCordinateA(getInt(byteBuf));
        newCommonInfo.setPhisCordinateB(getInt(byteBuf));
        newCommonInfo.setPhisCordinateC(getInt(byteBuf));

        if (newCommonInfo.isOptPa()) {
            PaDiscrimInfo paInfo = new PaDiscrimInfo();
            paInfo.setCpu(getShort(byteBuf));
            paInfo.setEvent_nbr(getShort(byteBuf));
            paInfo.setPa_ver(getShort(byteBuf));
            paInfo.setReserve(getShort(byteBuf));
            newCommonInfo.setPaDiscrimInfo(paInfo);

            int paEventLength = getInt(byteBuf);
            newCommonInfo.setPaEventVal(getString(byteBuf, paEventLength));
        }
        return newCommonInfo;
    }

    /**
     * イベントカウンタ情報の読み込み
     * @param byteBuf			ファイルバイトバッファ
     * @return		イベントカウンタ情報
     */
    private EventCounterInfo readEventCounterInfo(ByteBuffer byteBuf) {
        EventCounterInfo eventInfo = new EventCounterInfo();
        List<EventCounterGroup> groupList = new ArrayList<EventCounterGroup>();
        // イベントカウンタ数
        int eventCount = getInt(byteBuf);
        eventInfo.setEventcount(eventCount);
        for (int i = 0; i < eventCount; i++) {
            EventCounterGroup group = new EventCounterGroup();
            // カウンタグループ名長
            int length = getInt(byteBuf);
            // カウンタグループ名
            group.setGroupname(getString(byteBuf, length));
            // カウンタ詳細番号
            group.setDetailno(getInt(byteBuf));
            //基本情報
            group.setBaseInfo(readBaseInfo(byteBuf));
            // MPI情報
            if (this.commonInfo.isOptMpi()) {
                MpiInfo mpiInfo = readMpiInfo(byteBuf);
                group.setMpiInfo(mpiInfo);
            }
            // ハードウェアモニタ情報
            if (this.commonInfo.isOptPa()) {
                HardwareMonitorInfo hardwareInfo = readHardwareMonitorInfo(byteBuf);
                group.setHardwareInfo(hardwareInfo);
            }
            groupList.add(group);
        }
        if (groupList.size() > 0) {
            eventInfo.setEventGroupList(groupList);
        }
        return eventInfo;
    }

    /**
     * 基本情報の読み込み
     * @param byteBuf			ファイルバイトバッファ
     * @return   基本情報
     */
    private BaseInfo readBaseInfo(ByteBuffer byteBuf) {
        BaseInfo baseInfo = new BaseInfo();
        // カウンタの呼び出し回数	int
        baseInfo.setCallCount(getInt(byteBuf));
        // 経過時間	float
        baseInfo.setElapsTime(getFloat(byteBuf));
        // ユーザCPU時間	float
        baseInfo.setUserTime(getFloat(byteBuf));
        // システムＣＰＵ時間	float
        baseInfo.setSystemTime(getFloat(byteBuf));

        return baseInfo;
    }

    /**
     * MPI情報の読み込み
     * @param byteBuf			ファイルバイトバッファ
     * @return   MPI情報
     */
    private MpiInfo readMpiInfo(ByteBuffer byteBuf) {
        MpiInfo mpiInfo = new MpiInfo();
        // MPI関数の数
        int mpicount = getInt(byteBuf);
        mpiInfo.setMpiCount(mpicount);
        // MPI情報:MPI関数
        List<MpiFunction> mpiFunctionList = new ArrayList<MpiFunction>();
        for (int i = 0; i < mpicount; i++) {
            MpiFunction function = readMpiFunction(byteBuf);
            mpiFunctionList.add(function);
        }
        if (mpiFunctionList.size() > 0) {
            mpiInfo.setMpiFunctionList(mpiFunctionList);
        }
        return mpiInfo;
    }

    /**
     * MPI関数の読み込み
     * @param byteBuf			ファイルバイトバッファ
     * @return   MPI関数
     */
    private MpiFunction readMpiFunction(ByteBuffer byteBuf) {
        MpiFunction function = new MpiFunction();
        // MPI関数のIndex
        function.setMpiIndex(getInt(byteBuf));
        // 呼び出し回数
        function.setCallCount(getInt(byteBuf));
        // 経過時間
        function.setElapsTime(getFloat(byteBuf));
        // 待ち時間
        function.setWaitTime(getFloat(byteBuf));
        // メッセージ長
        function.setMessageLength(getLong(byteBuf));
        // メッセージ長が 0byte以上～4Kbyte未満の回数
        function.setCountMessage4k(getInt(byteBuf));
        // メッセージ長が 4Kbyte以上～64Kbyte未満の回数
        function.setCountMessage64k(getInt(byteBuf));
        // メッセージ長が 64Kbyte以上～1024Kbyte未満の回数
        function.setCountMessage1024k(getInt(byteBuf));
        // メッセージ長が 1024Kbyte以上の場合の回数
        function.setCountMessage1024kOver(getInt(byteBuf));

        return function;
    }

    /**
     * ハードウェアモニタ情報の読み込み
     * @param byteBuf			ファイルバイトバッファ
     * @return   ハードウェアモニタ情報
     */
    private HardwareMonitorInfo readHardwareMonitorInfo(ByteBuffer byteBuf) {
        HardwareMonitorInfo hardwareInfo = new HardwareMonitorInfo();
        // 測定スレッド数
        int threadcount = getInt(byteBuf);
        hardwareInfo.setThreadCount(threadcount);
        // ハードウェアモニタ情報(PA情報)テーブルリスト
        List<HardwarePaTable> paInfo = new ArrayList<HardwarePaTable>();
        for (int i = 0; i < threadcount; i++) {
            HardwarePaTable table = readHardwarePaTable(byteBuf);
            paInfo.add(table);
        }
        if (paInfo.size() > 0) {
            hardwareInfo.setPaInfo(paInfo);
        }
        return hardwareInfo;
    }


    /**
     * ハードウェアモニタ情報の読み込み
     * @param byteBuf			ファイルバイトバッファ
     * @return   ハードウェアモニタ情報
     */
    private HardwarePaTable readHardwarePaTable(ByteBuffer byteBuf) {
        HardwarePaTable table = new HardwarePaTable();
        // スレッド番号
        table.setThreadno(getInt(byteBuf));
        // ハードウェアモニタ情報
        int paEventLength = MAP_PA_INFO_LENGTH.get(this.commonInfo.getPaEventVal());
        double[] pa = new double[paEventLength];
        for (int j = 0; j < paEventLength; j++) {
            pa[j] = getDouble(byteBuf);
        }
        table.setPaTable(pa);
        return table;
    }


    /**
     * プロファイラファイルから読み込みを行う
     * @param profilerfile		プロファイラファイル
     * @throws Exception		読込エラー
     */
    @Override
    public void readFile(File profilerfile) throws Exception {
        readFile(profilerfile, this.endian);
    }

    /**
     * エンディアンを設定する
     * @param endian		エンディアン設定
     */
    @Override
    public void setEndian(int endian) {
        this.endian = endian;
    }

    /**
     * エンディアンを取得する
     * @return  エンディアン
     */
    @Override
    public int getEndian() {
        return this.endian;
    }

    /**
     * コスト情報リスト:ラインを取得する
     * @return		コスト情報リスト:ライン
     */
    @Override
    public ProfilerDprofData[] getCostInfoLine() {
        return null;
    }

    /**
     * コスト情報リスト:ループを取得する
     * @return		コスト情報リスト:ループ
     */
    @Override
    public ProfilerDprofData[] getCostInfoLoop() {
        return null;
    }


    /**
     * コスト情報リスト:手続を取得する
     * @return		コスト情報リスト:手続
     */
    @Override
    public ProfilerDprofData[] getCostInfoProcedure() {
        return null;
    }


    /**
     * イベントカウンタ情報を取得する
     * @return		イベントカウンタ情報
     */
    @Override
    public ProfilerEprofData[] getEprofEventCounterInfo() {
        if (this.eventInfo == null) return null;

        // カウンタグループリスト
        List<EventCounterGroup> eventGroupList = this.eventInfo.getEventGroupList();
        if (eventGroupList == null) return null;

        // プロファイラデータタイプ
        PROFILERINFO_TYPE type = null;
        // ハードウェアモニタ情報（ＰＡ情報）テーブル種別
        if (PA_EVENT_CACHE.equals(this.commonInfo.getPaEventVal())) {
            type = PROFILERINFO_TYPE.EVENTCOUNTER_CACHE;
        }
        else if (PA_EVENT_INSTRUCTIONS.equals(this.commonInfo.getPaEventVal())) {
            type = PROFILERINFO_TYPE.EVENTCOUNTER_INSTRUCTIONS;
        }
        else if (PA_EVENT_MEM_ACCESS.equals(this.commonInfo.getPaEventVal())) {
            type = PROFILERINFO_TYPE.EVENTCOUNTER_MEM_ACCESS;
        }
        else if (PA_EVENT_PERFORMANCE.equals(this.commonInfo.getPaEventVal())) {
            type = PROFILERINFO_TYPE.EVENTCOUNTER_PERFORMANCE;
        }
        else if (PA_EVENT_STATISTICS.equals(this.commonInfo.getPaEventVal())) {
            type = PROFILERINFO_TYPE.EVENTCOUNTER_STATISTICS;
        }

        List<ProfilerEprofData> list = new ArrayList<ProfilerEprofData>();
        for (EventCounterGroup group : eventGroupList) {
            ProfilerEprofData info = new ProfilerEprofData();
            info.setSymbol(group.getGroupname());
            BaseInfo baseinfo = group.getBaseInfo();
            if (baseinfo == null) continue;
            // カウンタの呼び出し回数
            info.setCallCount(baseinfo.getCallCount());
            // 経過時間
            info.setElapsTime(baseinfo.getElapsTime());
            // ユーザCPU時間
            info.setUserTime(baseinfo.getUserTime());
            // システムＣＰＵ時間
            info.setSystemTime(baseinfo.getSystemTime());
            // ハードウェアモニタ情報（ＰＡ情報）テーブル
            info.setHardwareInfo(group.getHardwareInfo());
            // ハードウェアモニタ情報（ＰＡ情報）テーブル種別
            info.setInfoType(type);

            list.add(info);
        }
        if (list.size() <= 0) {
            return null;
        }
        return list.toArray(new ProfilerEprofData[0]);
    }

    /**
     * 読込プロファイラファイル
     * @return 読込プロファイラファイル
     */
    @Override
    public File getProfFile() {
        return this.profFile;
    }

    /**
     * コールグラフ情報を取得する
     * @return		コールグラフ情報
     */
    @Override
    public ProfilerDprofData[] getDprofCallGraphInfo() {
        return null;
    }


    /**
     * プロファイラマジックキーを取得する
     * @return		マジックキー
     */
    @Override
    public String getFileType() {
        return FILE_ID_EPROF;
    }


    /**
     * PAイベント指定値(EPRFのみ)を取得する.
     *     Cache
     *     Instructions
     *     MEM_access
     *     Performance
     *     Statistics
     * @return 		PAイベント指定値(EPRFのみ)
     */
    @Override
    public String getPaEventName() {
        return commonInfo.getPaEventVal();
    }
}


