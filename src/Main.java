void main() {
    /*
    0 1 2
    1 2 3
     */
    Matrix m1 = new Matrix(
            2,
            3,
            (i, j)->i + j
    );

    /*
    0 0
    0 1
    0 2
     */
    Matrix m2 = new Matrix(
            3,
            2,
            (i, j)->i * j
    );

    /*
    0 5
    0 8
     */
    m1.setType(MultType.TRANSPOSED);
    var start = System.nanoTime();
    Matrix m3 = m1.multiply(m2);
    var end = System.nanoTime();

    System.out.println(m1);
    System.out.println(m2);
    System.out.println(m3);
    System.out.println("Затрачено: " + (end-start)/1_000_000f + "мс.");

    var r = new Random();

    Matrix m10 = new Matrix(2000, 1000,
            (_, _) -> r.nextInt()
    );
    Matrix m20 = new Matrix(1000, 2000,
            (_, _) -> r.nextInt()
    );

    for (var t : MultType.values()) {
        System.out.println(t);
        m10.setType(t);
        start = System.nanoTime();
        m10.multiply(m20);
        end = System.nanoTime();

        System.out.println("Затрачено: " + (end - start) / 1_000_000f + "мс.");
    }
}