package me.lovesasuna.mvplibrary.base

/**
 * @author LovesAsuna
 * @date 2020/10/10 21:14
 **/
class BaseResponse {
    /**
     * code : 200
     * msg : incorrect password
     * data : null
     */
    private var code = 0
    private var msg: String? = null
    private var data: Any? = null

    fun getCode(): Int {
        return code
    }

    fun setCode(code: Int) {
        this.code = code
    }

    fun getMsg(): String? {
        return msg
    }

    fun setMsg(msg: String?) {
        this.msg = msg
    }

    fun getData(): Any? {
        return data
    }

    fun setData(data: Any?) {
        this.data = data
    }
}