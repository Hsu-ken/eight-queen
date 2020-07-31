import javax.swing.*;



import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
public class Eight_Queen {
	//展示 棋盤
	public static void show_go_state(String go_state[][])
	{
		for(String[] i:go_state)
		{
			for(String j:i)
			{
				System.out.print(String.format("%4s",j )+" ");
			}
			System.out.println();
		}	
	}
	//重新隨機填入棋盤
	public static String[][] import_go_state_random(String go_state[][],int go_size)
	{
		//因為要重製 先清空
		go_state=new String[go_size][go_size];
		///*0-9對應Ascii 48-57 
		//A-Z 65-90 
		//a-z 97-122 
				
		int Q_ascii=81;
		char Q=(char)Q_ascii;
		Random random=new Random(System.currentTimeMillis());
		for(int i=0;i<go_size;i++)
		{
			//因為陣列是從0開始 所以0~(8-1)
			
			int random_i=random.nextInt(go_size-1);
			go_state[i][random_i]=Q+""+(i+1);
		}	
		return go_state;
	}
	public static boolean check_8Queen(String go_state[][],int go_size,LinkedHashMap<String,ArrayList<int[]>> Q_Site_Move)
	{
		boolean Right;
		
		
		//Q點名稱以及移動軌跡的map
		
		
		//Q點名稱以及位置的map
		HashMap<String,int[]> Q_Site=new HashMap<String,int[]>();
		//go_state 是目前棋盤的狀況 可以用來計算以及紀錄移動軌跡
		
		
		//先遍歷陣列 找出每個陣列Q的位置 放進Q_Site 再做計算
		for(int i=0;i<go_size;i++)
		{
			for(int j=0;j<go_size;j++)
			{
				
				String Q_Str=go_state[i][j];
				//代表不是空格 將皇后和其位置 放進Q_Site
				if(Q_Str!=(null))
				{		
					int tmep[]=new int[2];
					// 0是rwo 1是col
					tmep[0]=i;tmep[1]=j;
					
					Q_Site.put(Q_Str,tmep);			
				}
				
			}
		}
		//目前想法 第一次就讓他100000 後面用迴圈的概念 自然就正常 不可能第一次就local max
		int min_Comflict=0;
		
		//計算跑了幾次
		int round_Count=0;
		
		int conitnue_conflict_count=0;
		int temp_conflict=-1;
		//隨機選擇一個皇后 算出其餘所有空格的衝突數 接下來衝突數最低的走
		Random random=new Random(System.currentTimeMillis());
		while(true)
		{
			boolean first=true;
			//先設置 跳出條件 沒有更小的衝突數了就跳出 還有就繼續
			boolean No_SmellConflict=true;

			
			//隨機選擇一個皇后 0~7 -> 1~8 
			String Q_Name="Q"+(random.nextInt(go_size-1)+1);
			//移動到最少衝突的位置
			int move_Site[]=new int[2];

			//這是遍歷所有的空格 算出每個空格與其他皇后的衝突數(除了選中的皇后)

			for(int i=0;i<go_size;i++)
			{
				for(int j=0;j<go_size;j++)
				{		
					String Q_Str=go_state[i][j];
					//代表是空格 才去算
					if(Q_Str==(null))
					{
						int temp1[]=new int[2];
						temp1[0]=i;temp1[1]=j;
						int conflict=conflict_Num(Q_Name,temp1,go_size,Q_Site);
						if(first)
						{
							min_Comflict=conflict;
						}
						first=false;
						if(conflict<=min_Comflict)
						{
							move_Site=temp1;
							min_Comflict=conflict;
							//有更小的衝突數 繼續
							No_SmellConflict=false;
						}					
						//System.out.println(Q_Name+" "+"X:"+i+" Y:"+j+" Conflict:"+conflict);				
					}	
					//發現原本棋盤選中的Q 將它刪除 因為後面會直接更改位置
					if(Q_Name.equals(Q_Str))
					{
						go_state[i][j]=null;					
					}
				}
			}	
			//將終止條件改成 最小min_Comflict持續出現5000次 就終止
			//min_Comflict必須連續相同5000次 只要不連續一次 連續次數continue就會被重置成0
			if(temp_conflict==min_Comflict)
			{
				conitnue_conflict_count++;
				if(conitnue_conflict_count==5000)
				{
					No_SmellConflict=true;
				}
			}
			else
				conitnue_conflict_count=0;
			if(No_SmellConflict)
			{
				//這邊要寫的是 跳出去map要去重製 還沒結束
				Right=false;
				break;	
				/*
				if(round_Count%100==0)
				{
					show_go_state(go_state);
					//if(min_Comflict_samebreak==min_Comflict)
						//break;
					
					min_Comflict_samebreak=min_Comflict;
					//break;			
				}
				*/
			}
			temp_conflict=min_Comflict;
			round_Count++;
			
			System.out.println("move_Count:"+round_Count);
			System.out.println("min_Comflict:"+min_Comflict);
			
			System.out.println("Q_Name:"+Q_Name+",move_Site:"+move_Site[0]+":"+move_Site[1]);	
			
			//將原先go_state棋盤選中的棋子更改位置
			go_state[move_Site[0]][move_Site[1]]=Q_Name;

			//更新Q_Site 將Q_Name的位置更動
			Q_Site.put(Q_Name,move_Site);

			//紀錄Q_Site_Move的圖 暫時這樣想 可以先留著 比較想用arraylist將每個步驟的go_state紀錄
			//如果還不包含就加空的進去 有的話就直接add
			if(!Q_Site_Move.containsKey(Q_Name))
				Q_Site_Move.put(Q_Name,new ArrayList<int[]>());
			else
			{
				ArrayList<int[]> temp=Q_Site_Move.get(Q_Name);
				temp.add(move_Site);
				Q_Site_Move.put(Q_Name,temp);
			}
			//這邊判斷 Q_Site是否已經達到8皇后規定 有的話就跳開 沒有就繼續
			if(Eight_Queen_right(Q_Site))
			{	
				System.out.println("結束");
				//這邊要寫的是結束了
				Right=true;			
				break;
			}

		}
		
		return Right;
	}
	//算出該格跟其他皇后的衝突數(除了選中的皇后)
	public static int conflict_Num(String Q_Name,int temp1[],int go_size,HashMap<String,int[]> Q_Site)
	{
		int conflict=0;
							
		for(Map.Entry<String,int[]> e2:Q_Site.entrySet())
		{
			int temp2[]=e2.getValue();
			//位置相同便跳過
			if(Q_Name.equals(e2.getKey()))
				continue;			
			//上下左右相同 衝突		
			if(temp1[0]==temp2[0]||temp1[1]==temp2[1])
				conflict++;
			//斜角 斜率相同 衝突
			if(Math.abs(temp1[0]-temp2[0])==Math.abs(temp1[1]-temp2[1]))
				conflict++;
					
		}
		
		return conflict;
	}
	//算各皇后之間的衝突數 如果是0代表 規則達成
	public static boolean Eight_Queen_right(HashMap<String,int[]> Q_Site)
	{
		int conflict=0;
		for(Map.Entry<String,int[]> e1:Q_Site.entrySet())
		{			
			int temp1[]=new int[2];
			temp1=e1.getValue();
			for(Map.Entry<String,int[]> e2:Q_Site.entrySet())
			{
				int temp2[]=e2.getValue();
				//Q相同就跳過
				if(e1.getKey().equals(e2.getKey()))
					continue;			
				//上下左右相同 衝突		
				if(temp1[0]==temp2[0]||temp1[1]==temp2[1])
					conflict++;
				//斜角 斜率相同 衝突
				if(Math.abs(temp1[0]-temp2[0])==Math.abs(temp1[1]-temp2[1]))
					conflict++;
					
			}
		}
		//代表各皇后都沒有衝突 GG
		if(conflict==0)
			return true;
		else
			return false;
	}
	public static void main(String[] args)
	{
		//40大概要跑個2~3分鐘 30以下都是5秒內
		int go_size=8;
		
		String go_state[][]=new String[go_size][go_size];
		LinkedHashMap<String,ArrayList<int[]>> Q_Site_Move=new LinkedHashMap<String,ArrayList<int[]>>();
		int round=0;
		while(true)
		{
		System.out.println("round:"+round++);
		//重新填入資料
		go_state=import_go_state_random(go_state,go_size);
		//show資料
		show_go_state(go_state);
		
		//代表8皇后規則正確 結束 結束應該要有容器儲存go_state的軌跡 或是print go_state來看是否正確
		if(check_8Queen(go_state,go_size,Q_Site_Move))
		{
			System.out.println("round:"+round);
			System.out.println("right!");
			show_go_state(go_state);
			//要顯示移動軌跡 就遍歷 Q_Site_Move.value
			//System.out.println(Q_Site_Move.get("Q1").size());
			
			break;		
		}
		}
	}
	
	
}
