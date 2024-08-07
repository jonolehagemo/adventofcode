package math

// Greatest common divisor
fun gcd(a: Long, b: Long): Long =
    if (a <= 0L) 0L
    else if (b == 0L) a
    else gcd(b, a % b)

// Least common multiple
fun lcm(a: Long, b: Long): Long =
    if (0L < a && 0L < b) a * b / gcd(a, b)
    else 0L