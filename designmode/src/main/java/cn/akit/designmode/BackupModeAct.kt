package cn.akit.designmode

import cn.akit.frontlinepro.common.base.BaseAct
import com.alibaba.android.arouter.facade.annotation.Route

/**
 * Created by chenYuXuan on 2019/5/25.
 * email : southxvii@163.com
 */

@Route(path = "/design_mode/backup_mode")
class BackupModeAct : BaseAct() {

    override fun init() {

    }

    override fun provideViewRes(): Int {
        return R.layout.act_backup_mode
    }
}