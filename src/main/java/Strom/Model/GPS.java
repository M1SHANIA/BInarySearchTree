package Strom.Model;



public class GPS implements Comparable<GPS> {
    public int x;
    public int y;

    public GPS(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return x + " " + y;
    }

    @Override
    public int compareTo(GPS o) {
        return Math.toIntExact(binToInt(x,y)-binToInt(o.getX(),o.getY()));
    }
    private long binToInt(int x1, int y1){
        String[] xBin= Integer.toBinaryString(x1).split("");
        String[] yBin=Integer.toBinaryString(y1).split("");
        String  result="";
        if(xBin.length>yBin.length){
            for (int i = 0; i < xBin.length; i++) {
                if (i >= yBin.length) {
                    result += xBin[i];
                } else {
                    result += xBin[i] + yBin[i];
                }
            }
            }else{
                for (int i = 0; i < yBin.length; i++) {
                    if(i>=xBin.length){
                        result+=yBin[i];
                    }else{
                        result+=yBin[i]+xBin[i];
                    }
            }
        }
        return Long.parseLong(result, 2);

    }

}
