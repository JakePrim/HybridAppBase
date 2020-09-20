package news

import java.lang.RuntimeException

//设备接口
interface InputDevice {
    fun input(event: Any)
}

//各种具体输入设备接口
//接口可以作为子类
interface UsbInputDevice : InputDevice

interface BLEInputDevice : InputDevice

//usb 鼠标
abstract class UsbMouse(val name: String) : UsbInputDevice {
    override fun input(event: Any) {

    }

    override fun toString(): String {
        return name
    }
}

//定义电脑
class Complter {
    fun addUsbInputDevice(event: UsbInputDevice) {
        //插入输入设备
        println("插入了输入Usb设备:$event")
    }

    fun addBLEInputDevice(input: BLEInputDevice) {
        println("插入了蓝牙设备:$input")
    }

    fun addInputDevice(input: InputDevice) {
        when (input) {
            is BLEInputDevice -> addBLEInputDevice(input)
            is UsbInputDevice -> addUsbInputDevice(input)
            else -> throw RuntimeException("不支持的设备$input")
        }
    }
}

class LoJiMouse(name: String) : UsbMouse(name) {
    override fun input(event: Any) {
        super.input(event)
    }

    override fun toString(): String {
        return super.toString()
    }
}

fun main() {
    val complter = Complter()

    val usbM = LoJiMouse("罗技鼠标")

    complter.addInputDevice(usbM)
}

