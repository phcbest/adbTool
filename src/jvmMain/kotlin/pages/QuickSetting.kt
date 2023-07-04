package pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import components.*
import entity.KeyMapper
import theme.GOOGLE_RED
import theme.GOOGLE_YELLOW
import utils.execute

@Composable
fun QuickSetting() {
    val keyPre = "adb shell input keyevent "
    val keyMapperList1 = listOf(
        KeyMapper("task.png", 187, "任务列表"),
        KeyMapper("home.png", 3, "返回桌面"),
        KeyMapper("back.png", 4, "返回"),
        KeyMapper("power.png", 26, "电源")
    )
    val keyMapperList2 = listOf(
        KeyMapper("plus.png", 24, "增加音量"),
        KeyMapper("minus.png", 25, "减少音量"),
        KeyMapper("up.png", 221, "增加亮度"),
        KeyMapper("down.png", 220, "减少亮度"),

        )

    val keyMapperList3 = listOf(
        KeyMapper("down.png", 1, "显示状态栏"),
        KeyMapper("up.png", 2, "隐藏状态栏"),
        KeyMapper("mute.png", 164, "打开静音"),
        KeyMapper("settings.png", 0, "进入设置")
    )
    val scroll = rememberScrollState()

    Column (modifier = Modifier.fillMaxSize().fillMaxHeight().verticalScroll(scroll)) {
        General(title = "按键模拟", height = 3, content = {
            ContentMoreRowColumn {
                ContentNRow {
                    keyMapperList1.forEach {
                        Item(it.icon, it.name) {
                            execute(keyPre + it.key)
                        }
                    }
                }
                ContentNRow {
                    keyMapperList2.forEach {
                        Item(it.icon, it.name) {
                            execute(keyPre + it.key)
                        }
                    }
                }
                ContentNRow {
                    Item(keyMapperList3[0].icon, keyMapperList3[0].name) {
                        execute("adb shell service call statusbar 1")
                    }
                    Item(keyMapperList3[1].icon, keyMapperList3[1].name) {
                        execute("adb shell service call statusbar 2")
                    }
                    Item(keyMapperList3[2].icon, keyMapperList3[2].name) {
                        execute(keyPre + keyMapperList3[2].key)
                    }
                    Item(keyMapperList3[3].icon, keyMapperList3[3].name) {
                        execute("adb shell am start  -n com.android.settings/com.android.settings.Settings")
                    }
                }
            }
        })
        General(title = "应用相关", color = GOOGLE_RED, height = 2, content = {
            ContentMoreRowColumn {
                ContentNRow {
                    Item("file.png", "测试1")
                    Item("file.png", "测试1")
                    Item("file.png", "测试1")
                    Item("file.png", "查看当前Activity")
                }
                ContentNRow {
                    Item("file.png", "测试1")
                    Item("file.png", "测试1")
                    Item("file.png", "测试1")
                    Item("file.png", "查看当前Activity")
                }
            }
        })
        General(title = "测试功能", color = GOOGLE_YELLOW, content = {
            ContentRow {
                Item("file.png", "测试1")
                Item("file.png", "测试1")
                Item("file.png", "测试1")
                Item("file.png", "查看当前Activity")
            }
        })
    }
}
