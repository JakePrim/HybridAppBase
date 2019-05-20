package overiloads

class Overloads {
    fun a(): Int {
        return 0
    }

    fun a(int: Int = 0): Int {
        return int
    }
}