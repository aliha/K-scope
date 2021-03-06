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

package jp.riken.kscope.language;

/**
*
* 繰り返し処理を表現したクラス.<br>
* FortranにおけるDOループに該当するもの.
*
* @author RIKEN
*
*/
public class Repetition extends Block {
	/** シリアル番号 */
	private static final long serialVersionUID = -2221953302518033528L;
	/** DO文:index変数 */
    private Variable iterator;
    /** DO文:初期値 */
    private Expression initIterator;
    /** DO文:最大値 */
    private Expression endCondition;
    /** DO文:ステップインターバル */
    private Expression step;

    /**
     * コンストラクタ.
     */
	Repetition() {
        super();
	}

    /**
     * コンストラクタ.
     *
     * @param mama 親ブロック
     */

	Repetition(Block mama) {
        super(mama);
	}

    /**
     * コンストラクタ.
     *
     * @param itrtr ループ制御変数
     * @param initItrtr 始値
     * @param endCndtn 終値
     * @param stp 刻み幅
     */
    public Repetition(Variable itrtr,
            Expression initItrtr, Expression endCndtn,
            Expression stp) {
        this();
        this.iterator = itrtr;
        this.initIterator = initItrtr;
        this.endCondition = endCndtn;
        this.step = stp;
	}
    /**
     * ブロックタイプの取得。
     *
     * @return BlockType.REPETITION
     */
    public BlockType getBlockType() {
        return BlockType.REPETITION;
    }

    /**
     * ループ制御変数の設定.
     *
     * @param itrtr ループ制御変数
     */
    protected void setIterator(Variable itrtr) {
        iterator = itrtr;
    }
    /**
     * ループ制御変数の取得.
     *
     * @return ループ制御変数
     */
    public Variable getIterator() {
        return iterator;
	}

    /**
     * 始値の設定.
     *
     * @param initItrtr 始値
     */
    protected void setInitIterator(Expression initItrtr) {
        initIterator = initItrtr;
    }
    /**
     * 始値の取得.
     *
     * @return 始値
     */
    public Expression getInitIterator() {
        return initIterator;
	}

    /**
     * 終値の設定.
     *
     * @param endCndtn 終値
     */
    protected void setEndCondition(Expression endCndtn) {
        endCondition = endCndtn;
    }
    /**
     * 終値の取得.
     *
     * @return 終値
     */
    public Expression getEndCondition() {
        return endCondition;
	}

    /**
     * 刻み幅の設定.
     *
     * @param stp 刻み幅
     */
    protected void setStep(Expression stp) {
        step = stp;
    }
    /**
     * 刻み幅の取得.
     *
     * @return 刻み幅
     */
    public Expression getStep() {
        return step;
	}

    /**
     * メンバー変数の設定.
     *
     * @param itrtr
     *           ループ制御変数
     * @param initItrtr
     *           始値
     * @param endCndtn
     *           終値
     * @param stp
     *           刻み幅
     */
    protected void setProperty(Variable itrtr,
            Expression initItrtr, Expression endCndtn,
            Expression stp) {
        iterator = itrtr;
        initIterator = initItrtr;
        endCondition = endCndtn;
		step = stp;
		// 親DO文をセットする
		if (iterator != null) {
			iterator.setParentStatement(this);
		}
		if (initIterator != null) {
			initIterator.setParentStatement(this);
		}
		if (endCondition != null) {
			endCondition.setParentStatement(this);
		}
		if (step != null) {
			step.setParentStatement(this);
		}
	}
}
