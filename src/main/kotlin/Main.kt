import java.util.*
import kotlin.time.measureTime

val fmap = mapOf(
    "f1" to ::f1,
    "f2" to ::f2,
    "f3" to ::f3,
    "f4" to ::f4,
    "f10" to ::f10,
    "f11" to ::f11,
    "g1" to ::g1,
    "d1" to ::d1,
)

val cmap = mutableMapOf(
    "f1" to 0,
    "f2" to 0,
    "f3" to 0,
    "f4" to 0,
    "f10" to 0,
    "f11" to 0,
    "g1" to 0,
    "d1" to 0,
)

class Cnt() {
    var v = 0
    var v0 = 0

    fun inc() {
        v++
    }

    fun dif() : Int {
        val d = v - v0
        v0 = v
        return d
    }
}

var lc1 = 10

var cnt = Cnt()
var cntB = Cnt()


fun exec(c: String) : String {
    return fmap[c]?.invoke(c)!!.also { cmap[c] = if (cmap[c] != null) cmap[c]!!.plus(1) else 1 }
}

var flags = HashMap<String, String>()
var argl = listOf<String>()

fun main(args: Array<String>) {
    println("Hello World!")

    flags = flagAsMap(args)
    argl = argAsList(args)

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${flags} ${argl}")


    lc1 = 100_000

    val li1_1 = listOf("f1", "f1", "f1", "f1", "f1", "f10", "f11", "f2", "f1", "f3", "g1")
    val li1_2 = listOf("f1")

    (if ( flags.get("all") != null) li1_1 else li1_2).forEach {c ->
        var s = ""
        var mt = measureTime { s = exec(c) }
        println("It took ${cmap[c]} $s $mt")
    }

    if ( flags.get("dhry") != null) {
        listOf("d1").forEach { c ->
            var s = ""
            var mt = measureTime { s = exec(c) }
            println("It took ${cmap[c]} $s $mt")
        }
    }

    lc1 = 3000

    listOf("f4").forEach {c ->
        var s = ""
        var mt = measureTime { s = exec(c) }
        println("It took ${cmap[c]} $s $mt")
    }
}


fun f1(s: String = "f1"): String  {
    var s1 = ""

    (0..<lc1).forEach {
        s1 = it.toString() + s1
        cnt.inc()
    }
    return format(s, s1)
}

fun f10(s: String = "f10"): String  {
    var s1 = ""

    (0..<lc1).forEach {
        s1 += it.toString()
        cnt.inc()
    }
    return format(s, s1)
}

fun f11(s: String = "f11"): String  {
    var s1 = 0

    (0..<lc1).forEach {
        s1 += it
        cnt.inc()
    }
    return format(s, "" + s1)
}

fun f2(s: String = "f2"): String  {
    var s1 = ""

    for (ix in 0..<lc1) {
        s1 = ix.toString() + s1
        cnt.inc()
    }
    return format(s, s1)
}

fun f3(s: String = "f3"): String {
    var s1 = ""

    var i = 0
    while (i < lc1) {
        s1 = i.toString() + s1
        i++
        cnt.inc()
    }
    return format(s, s1)
}

fun f4(s: String = "f4"): String {
    var s1 = ""

    var i = 0
    while (i < lc1) {
        s1 = f3().replace(" +".toRegex(), " ") + s1
        i++
        cntB.inc()
    }
    return formatB(s, s1)
}

fun d1(s: String = "d1"): String {
    Dhry.main(arrayOf("100000000"))
    return Dhry.dhry_total_val.toString()
}

fun g1(s: String = "g1"): String {
    var i = 0
    var s1 = "";

    repeat(1) {
        var i1 = 0
        val calc = Calc()

        while (i1 < lc1) {
            s1 += calc.a_s()
            cnt.inc()
            i1++
            i++
        }
    }

    return formatB(s, "" + i.toString() + "," + s1)
}

class Calc {
    val cntCalc = Cnt()

    fun a_s() : String {
        a(1000, 1, 1000)
        return cntCalc.v.toString()
    }

    fun a(a: Int, b: Int, c: Int) : Int {
        return if ( c == 0 ) a + b(b.toDouble(), a.toDouble()).toInt() else a + b + a(a, b, c-1)
    }

    fun b(x: Double, y: Double) : Double {
        val v1 = x + y
        val v2 = x * y
        val v3 = x / (if ( y == 0.0 ) 1.0 else y)
        cntCalc.inc()
        return v1 + v2 - v3
    }
}

fun format(s: String, s1: String): String {
    return pad(s, 3) + pad("" + cnt.dif() , 9) + " " + pad(s1.take(100), 100) + " " + s1.length
}

fun formatB(s: String, s1: String): String {
    return pad(s, 3) + pad("" + cntB.dif() , 9) + " " + pad(s1.take(100), 100) + " " + s1.length
}

fun pad(s: String, i: Int): String {
    return if (s.length >= i ) s else " ".repeat(i - s.length) + s
}

// - - - - - - - - - - - - - - - - - - - - - - - - - - -
fun flagAsMap(argv: Array<String>): HashMap<String, String> {
    val argl: MutableList<String?> = Arrays.asList(*argv)
    val flag: HashMap<String, String> = HashMap()
    for (i in argl.indices) {
        val s = argl[i] as String
        if (s.startsWith("-")) {
            val ss = s.substring(1)
            val ix = ss.indexOf('=')
            if (ix != -1) {
                val sk = ss.substring(0, ix)
                val sv = ss.substring(ix + 1)
                if (sv.indexOf(',') == -1) flag[sk] = sv else {
                    val sa = sv.split(",")
                    flag[sk] = sv
                    flag["[SundryUtil;$sk"] = sa.toString()
                }
            } else {
                flag[ss] = ""
            }
        }
    }
    return flag
}

fun argAsList(argv: Array<String>): List<String> {
    var argl: List<String> = LinkedList()
    for (av in argv) {
        if (!av.startsWith("-")) {
            argl += av
        }
    }
    return argl
}
