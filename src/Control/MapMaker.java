package Control;

public abstract class MapMaker {
    private MapMaker(){}

    private static String GetPlant(){
        int x = (int) (Math.random() * 1000);
        x %= 100;
        if (x <= 23)    return "SunFlower";
        else if (x<=75) return "PeaShooter";
        else if (x<=87) return "Repeater";
        else return "WallNut";
        // 0~23  -> Sunflower
        // 24~75 -> Shoot
        // 76~87 -> Repeat
        // 88~99 -> WallNut
    }

    public static String [][] GetMap(int n, int m) {
        String[][] ans = new String[n][m];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                ans[i][j] = GetPlant();
        return ans;
    }
}
