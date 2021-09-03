package utils;

public class Pair<L,R> {
    private L l;
    private R r;
    private int delPos;

    public Pair(L l, R r){
        this.l = l;
        this.r = r;
    }
    public L getL(){ return l; }
    public R getR(){ return r; }
    public int getDelPos() {return delPos;}
    public void setL(L l){ this.l = l; }
    public void setR(R r){ this.r = r; }
    public void setDelPos(int delPos) {this.delPos = delPos; }
}