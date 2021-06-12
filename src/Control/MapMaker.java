package Control;

public abstract class MapMaker {
    private MapMaker(){}

    
	/** 
	 * @return String 按概率生成一种植物
	 */
	private static String GetPlant(){
        int x = (int) (Math.random() * 1000);
        x %= 100;
        if (x <= 32)    return "SunFlower";
        else if (x<=75) return "PeaShooter";
        else if (x<=87) return "Repeater";
        else if (x<=94) return "WallNut";
        else return "Potato";
        // 0~23  -> Sunflower
        // 24~75 -> Shoot
        // 76~87 -> Repeat
        // 88~99 -> WallNut
    }

    
	/** 
	 * @param n 地图行数
	 * @param m 地图列数
	 * @return String[][] 生成的植物,以名称方式填充
	 */
	public static String [][] GetMap(int n, int m) {
        String[][] ans = new String[n][m];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                ans[i][j] = GetPlant();
        return ans;
    }
}
