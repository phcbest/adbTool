package pages

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import components.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import theme.GOOGLE_BLUE
import theme.GOOGLE_GREEN
import theme.GOOGLE_RED
import theme.GOOGLE_YELLOW
import utils.BashUtils
import utils.ClipboardUtil
import utils.GenerexUtils


/**
 * 命令字生成页面
 */

//全局变量，页面切换保留数据
val text1 = mutableStateOf("")
val text2 = mutableStateOf("")

@OptIn(DelicateCoroutinesApi::class)
@Preview
@Composable
fun CommandGeneral() {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth().weight(1f)) {
                CommandText(str = text1, hint = "输入正则表达式")
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                CommandButton("生成") {
                    try {
                        if (text1.value.isBlank()) {
                            if (!showToast.value) {
                                currentToastTask.value = "CommandGnerate"
                                toastText.value = "内容不可空"
                                showToast.value = true
                            } else {
                                if (currentToastTask.value == "CommandGnerate")
                                    return@CommandButton
                                GlobalScope.launch{
                                    delay(1000)
                                    currentToastTask.value = "CommandGnerate"
                                    toastText.value = "内容不可空"
                                    showToast.value = true
                                }

                            }
                            return@CommandButton
                        }
                        text2.value = GenerexUtils.generateAll(text1.value)
                    } catch (_: Exception) {
                        dialogTitle.value = "警告"
                        dialogTitleColor.value = GOOGLE_RED
                        dialogText.value = "请检查正则格式是否正确!!!"
                        showingDialog.value = true
                    }
                }
                CommandButton("粘贴", backgroundColor = GOOGLE_GREEN) {
                    text1.value = ClipboardUtil.getSysClipboardText()
                }
                CommandButton("复制", backgroundColor = GOOGLE_YELLOW) {
                    if (text2.value.isBlank())
                        return@CommandButton
                    ClipboardUtil.setSysClipboardText(text2.value)
                    if (!showToast.value) {
                        currentToastTask.value = "CommandCopy"
                        toastText.value = "结果已复制"
                        showToast.value = true
                    } else {
                        if (currentToastTask.value == "CommandCopy")
                            return@CommandButton
                        GlobalScope.launch {
                            delay(1000)
                            currentToastTask.value = "CommandCopy"
                            toastText.value = "结果已复制"
                            showToast.value = true
                        }
                    }
                }
                CommandButton("清空", backgroundColor = GOOGLE_RED) {
                    if (text2.value.isBlank() && text1.value.isBlank())
                        return@CommandButton
                    text1.value = ""
                    text2.value = ""
                    if (!showToast.value) {
                        currentToastTask.value = "CommandClear"
                        toastText.value = "内容已清空"
                        showToast.value = true
                    } else {
                        if (currentToastTask.value == "CommandClear")
                            return@CommandButton
                        GlobalScope.launch {
                            delay(1000)
                            currentToastTask.value = "CommandClear"
                            toastText.value = "内容已清空"
                            showToast.value = true
                        }
                    }
                }
            }
            Row(modifier = Modifier.fillMaxWidth().weight(2f)) {
                CommandText(str = text2, hint = "结果")
            }
            if (showingDialog.value)
                SimpleDialog(
                    showingDialog,
                    title = dialogTitle.value,
                    titleColor = dialogTitleColor.value,
                    text = dialogText.value
                )
        }
        Toast(showToast, toastText, background = toastBgColor.value, textColor = toastTextColor.value)
    }
}


@Composable
fun CommandText(str: MutableState<String>, hint: String) {
    val scroll = rememberScrollState()
    TextField(
        str.value,
        modifier = Modifier.fillMaxSize().scrollable(scroll, Orientation.Vertical).fillMaxHeight(),
        placeholder = { Text(hint) },
        trailingIcon = {
            if (str.value.isNotEmpty()) {
                Box(
                    contentAlignment = Alignment.BottomEnd,
                    modifier = Modifier.fillMaxHeight().padding(bottom = 8.dp)
                ) {
                    Icon(Icons.Default.Close, null, modifier = Modifier.clickable {
                        str.value = ""
                    }.hoverable(interactionSource = remember { MutableInteractionSource() }))
                }
            }
        },
        onValueChange = {
            str.value = it
        })
}

@Composable
fun CommandButton(
    str: String,
    backgroundColor: Color = GOOGLE_BLUE,
    textColor: Color = Color.White,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(backgroundColor = backgroundColor),
        modifier = Modifier.padding(end = 5.dp, top = 5.dp, bottom = 5.dp)
    ) {
        Text(str, color = textColor)
    }
}


