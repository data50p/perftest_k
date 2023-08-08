import kotlin.time.measureTime

val fmap = mapOf(
    "f1" to ::f1,
    "f2" to ::f2,
    "f3" to ::f3,
    "f4" to ::f4,
    "f10" to ::f10,
    "f11" to ::f11,
    )


fun exec(c: String) : String {
    return fmap[c]?.invoke("" + c)!!
}

fun main(args: Array<String>) {
    println("Hello World!")

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")

    var s = ""

    listOf("f1", "f1", "f1", "f1", "f1", "f10", "f11", "f2", "f1", "f3").forEach {c ->
        var mt = measureTime { s = exec(c) }
        println("It took $s $mt")
    }

    lc1 = 3000

    listOf("f4").forEach {c ->
        var mt = measureTime { s = exec(c) }
        println("It took $s $mt")
    }
}

var lc1 = 100_000

var cnt0 = 0
var cnt = 0
var cntB0 = 0
var cntB = 0

fun f1(s: String = "f1"): String  {
    var s1 = ""

    (0..<lc1).forEach {
        s1 = it.toString() + s1
        cnt++
    }
    return format(s, s1)
}

fun f10(s: String = "f10"): String  {
    var s1 = ""

    (0..<lc1).forEach {
        s1 += it.toString()
        cnt++
    }
    return format(s, s1)
}

fun f11(s: String = "f11"): String  {
    var s1 = 0

    (0..<lc1).forEach {
        s1 += it
        cnt++
    }
    return format(s, "" + s1)
}

fun f2(s: String = "f2"): String  {
    var s1 = ""

    for (ix in 0..<lc1) {
        s1 = ix.toString() + s1
        cnt++
    }
    return format(s, s1)
}

fun f3(s: String = "f3"): String {
    var s1 = ""

    var i = 0
    while (i < lc1) {
        s1 = i.toString() + s1
        i++
        cnt++
    }
    return format(s, s1)
}

fun f4(s: String = "f4"): String {
    var s1 = ""

    var i = 0
    while (i < lc1) {
        s1 = f3().replace(" +".toRegex(), " ") + s1
        i++
        cntB++
    }
    return formatB(s, s1)
}

fun format(s: String, s1: String): String {
    val cnt1 = cnt - cnt0
    cnt0 = cnt
    return pad(s, 3) + pad("" + cnt1 , 9) + " " + pad(s1.take(100), 100) + " " + s1.length
}

fun formatB(s: String, s1: String): String {
    val cnt1 = cnt - cnt0
    cnt0 = cnt
    val cntB1 = cntB - cntB0
    cntB0 = cntB
    return pad(s, 3) + pad("" + cntB1 , 9) + " " + pad(s1.take(100), 100) + " " + s1.length
}

fun pad(s: String, i: Int): String {
    if (s.length >= i )
        return s
    return " ".repeat(i - s.length) + s
}
