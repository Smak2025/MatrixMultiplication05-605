import com.sun.jdi.event.MonitorWaitedEvent;

import java.util.Arrays;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;

public class Matrix {
    private int[][] matrix;
    private MultType type = MultType.DIRECT;
    public Matrix(int rows,
                  int cols,
                  BiFunction<Integer,Integer,Integer> filler)
    {
        matrix = new int[rows][cols];
        fill(filler);
    }
    private void fill(BiFunction<Integer,Integer,Integer> filler)
    {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = filler.apply(i,j);
            }
        }
    }

    public Matrix multiply(Matrix other){
        return switch (type){
            case DIRECT -> multiplyDirect(other);
            case TRANSPOSED -> multiplyTransposed(other);
        };
    }
    private Matrix multiplyDirect(Matrix other){
        Matrix newMatrix = new Matrix(
                getRowCount(),
                other.getColCount(),
                (_,_)->0
        );
        for (int i = 0; i <getRowCount(); i++) {
            for (int j = 0; j < other.getColCount() ; j++) {
                for (int k = 0; k < other.getRowCount() ; k++) {
                    newMatrix.matrix[i][j] += matrix[i][k] * other.matrix[k][j];
                }
            }
        }
        return newMatrix;
    }

    private Matrix multiplyTransposed(Matrix other){
        Matrix newMatrix = new Matrix(
                getRowCount(),
                other.getColCount(),
                (_,_)->0
        );
        var otherT = other.transpose();
        var iMax = getRowCount();
        var jMax = otherT.getRowCount();
        var kMax = getColCount();
        for (int i = 0; i < iMax; i++) {
            for (int j = 0; j < jMax; j++) {
                for (int k = 0; k < kMax; k++) {
                    newMatrix.matrix[i][j] += matrix[i][k] * otherT.matrix[j][k];
                }
            }
        }
        return newMatrix;
    }

    public Matrix transpose(){
        return new Matrix(
                getColCount(),
                getRowCount(),
                (i, j) -> matrix[j][i]
        );
    }

    public int getRowCount(){
        return matrix.length;
    }
    public int  getColCount(){
        return matrix[0].length;
    }

    public MultType getType() {
        return type;
    }

    public void setType(MultType type) {
        this.type = type;
    }

    public String toString() {
        var sb = new StringBuilder();
        for (int i = 0; i < getRowCount(); i++) {
            sb.append(Arrays.toString(matrix[i]));
            sb.append("\n");
        }
        return sb.toString();
    }
}

