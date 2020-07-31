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
	//�i�� �ѽL
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
	//���s�H����J�ѽL
	public static String[][] import_go_state_random(String go_state[][],int go_size)
	{
		//�]���n���s ���M��
		go_state=new String[go_size][go_size];
		///*0-9����Ascii 48-57 
		//A-Z 65-90 
		//a-z 97-122 
				
		int Q_ascii=81;
		char Q=(char)Q_ascii;
		Random random=new Random(System.currentTimeMillis());
		for(int i=0;i<go_size;i++)
		{
			//�]���}�C�O�q0�}�l �ҥH0~(8-1)
			
			int random_i=random.nextInt(go_size-1);
			go_state[i][random_i]=Q+""+(i+1);
		}	
		return go_state;
	}
	public static boolean check_8Queen(String go_state[][],int go_size,LinkedHashMap<String,ArrayList<int[]>> Q_Site_Move)
	{
		boolean Right;
		
		
		//Q�I�W�٥H�β��ʭy��map
		
		
		//Q�I�W�٥H�Φ�m��map
		HashMap<String,int[]> Q_Site=new HashMap<String,int[]>();
		//go_state �O�ثe�ѽL�����p �i�H�Ψӭp��H�ά������ʭy��
		
		
		//���M���}�C ��X�C�Ӱ}�CQ����m ��iQ_Site �A���p��
		for(int i=0;i<go_size;i++)
		{
			for(int j=0;j<go_size;j++)
			{
				
				String Q_Str=go_state[i][j];
				//�N���O�Ů� �N�ӦZ�M���m ��iQ_Site
				if(Q_Str!=(null))
				{		
					int tmep[]=new int[2];
					// 0�Orwo 1�Ocol
					tmep[0]=i;tmep[1]=j;
					
					Q_Site.put(Q_Str,tmep);			
				}
				
			}
		}
		//�ثe�Q�k �Ĥ@���N���L100000 �᭱�ΰj�骺���� �۵M�N���` ���i��Ĥ@���Nlocal max
		int min_Comflict=0;
		
		//�p��]�F�X��
		int round_Count=0;
		
		int conitnue_conflict_count=0;
		int temp_conflict=-1;
		//�H����ܤ@�ӬӦZ ��X��l�Ҧ��Ů檺�Ĭ�� ���U�ӽĬ�Ƴ̧C����
		Random random=new Random(System.currentTimeMillis());
		while(true)
		{
			boolean first=true;
			//���]�m ���X���� �S����p���Ĭ�ƤF�N���X �٦��N�~��
			boolean No_SmellConflict=true;

			
			//�H����ܤ@�ӬӦZ 0~7 -> 1~8 
			String Q_Name="Q"+(random.nextInt(go_size-1)+1);
			//���ʨ�ֽ̤Ĭ𪺦�m
			int move_Site[]=new int[2];

			//�o�O�M���Ҧ����Ů� ��X�C�ӪŮ�P��L�ӦZ���Ĭ��(���F�襤���ӦZ)

			for(int i=0;i<go_size;i++)
			{
				for(int j=0;j<go_size;j++)
				{		
					String Q_Str=go_state[i][j];
					//�N��O�Ů� �~�h��
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
							//����p���Ĭ�� �~��
							No_SmellConflict=false;
						}					
						//System.out.println(Q_Name+" "+"X:"+i+" Y:"+j+" Conflict:"+conflict);				
					}	
					//�o�{�쥻�ѽL�襤��Q �N���R�� �]���᭱�|��������m
					if(Q_Name.equals(Q_Str))
					{
						go_state[i][j]=null;					
					}
				}
			}	
			//�N�פ����令 �̤pmin_Comflict����X�{5000�� �N�פ�
			//min_Comflict�����s��ۦP5000�� �u�n���s��@�� �s�򦸼�continue�N�|�Q���m��0
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
				//�o��n�g���O ���X�hmap�n�h���s �٨S����
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
			
			//�N���go_state�ѽL�襤���Ѥl����m
			go_state[move_Site[0]][move_Site[1]]=Q_Name;

			//��sQ_Site �NQ_Name����m���
			Q_Site.put(Q_Name,move_Site);

			//����Q_Site_Move���� �Ȯɳo�˷Q �i�H���d�� ����Q��arraylist�N�C�ӨB�J��go_state����
			//�p�G�٤��]�t�N�[�Ū��i�h �����ܴN����add
			if(!Q_Site_Move.containsKey(Q_Name))
				Q_Site_Move.put(Q_Name,new ArrayList<int[]>());
			else
			{
				ArrayList<int[]> temp=Q_Site_Move.get(Q_Name);
				temp.add(move_Site);
				Q_Site_Move.put(Q_Name,temp);
			}
			//�o��P�_ Q_Site�O�_�w�g�F��8�ӦZ�W�w �����ܴN���} �S���N�~��
			if(Eight_Queen_right(Q_Site))
			{	
				System.out.println("����");
				//�o��n�g���O�����F
				Right=true;			
				break;
			}

		}
		
		return Right;
	}
	//��X�Ӯ���L�ӦZ���Ĭ��(���F�襤���ӦZ)
	public static int conflict_Num(String Q_Name,int temp1[],int go_size,HashMap<String,int[]> Q_Site)
	{
		int conflict=0;
							
		for(Map.Entry<String,int[]> e2:Q_Site.entrySet())
		{
			int temp2[]=e2.getValue();
			//��m�ۦP�K���L
			if(Q_Name.equals(e2.getKey()))
				continue;			
			//�W�U���k�ۦP �Ĭ�		
			if(temp1[0]==temp2[0]||temp1[1]==temp2[1])
				conflict++;
			//�ר� �ײv�ۦP �Ĭ�
			if(Math.abs(temp1[0]-temp2[0])==Math.abs(temp1[1]-temp2[1]))
				conflict++;
					
		}
		
		return conflict;
	}
	//��U�ӦZ�������Ĭ�� �p�G�O0�N�� �W�h�F��
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
				//Q�ۦP�N���L
				if(e1.getKey().equals(e2.getKey()))
					continue;			
				//�W�U���k�ۦP �Ĭ�		
				if(temp1[0]==temp2[0]||temp1[1]==temp2[1])
					conflict++;
				//�ר� �ײv�ۦP �Ĭ�
				if(Math.abs(temp1[0]-temp2[0])==Math.abs(temp1[1]-temp2[1]))
					conflict++;
					
			}
		}
		//�N��U�ӦZ���S���Ĭ� GG
		if(conflict==0)
			return true;
		else
			return false;
	}
	public static void main(String[] args)
	{
		//40�j���n�]��2~3���� 30�H�U���O5��
		int go_size=8;
		
		String go_state[][]=new String[go_size][go_size];
		LinkedHashMap<String,ArrayList<int[]>> Q_Site_Move=new LinkedHashMap<String,ArrayList<int[]>>();
		int round=0;
		while(true)
		{
		System.out.println("round:"+round++);
		//���s��J���
		go_state=import_go_state_random(go_state,go_size);
		//show���
		show_go_state(go_state);
		
		//�N��8�ӦZ�W�h���T ���� �������ӭn���e���x�sgo_state���y�� �άOprint go_state�ӬݬO�_���T
		if(check_8Queen(go_state,go_size,Q_Site_Move))
		{
			System.out.println("round:"+round);
			System.out.println("right!");
			show_go_state(go_state);
			//�n��ܲ��ʭy�� �N�M�� Q_Site_Move.value
			//System.out.println(Q_Site_Move.get("Q1").size());
			
			break;		
		}
		}
	}
	
	
}
