package adventure;

public class MapBlock {
	private String title = "";
	private String desc = "";
	private byte n = 1;
	private byte s = 1;
	private byte e = 1;
	private byte w = 1;
	public int[] itemsHere = new int[20];
	public int itemsCount = 0;
	
	public void setTitle(String t)
	{
		title = t;
	}
	public String getTitle()
	{
		return title;
	}
	public void setDesc(String d)
	{
		desc = d;
	}
	public String getDesc()
	{
		return desc;
	}
	public void setN(byte n)
	{
		this.n = n;
	}
	public byte getN()
	{
		return n;
	}
	public void setS(byte s)
	{
		this.s = s;
	}
	public byte getS()
	{
		return s;
	}
	public void setE(byte e)
	{
		this.e = e;
	}
	public byte getE()
	{
		return e;
	}
	public void setW(byte w)
	{
		this.w = w;
	}
	public byte getW()
	{
		return w;
	}
	
	public boolean isWall(char dir) //'n','s','e','w'
	{
		
		boolean answer = true;
		
		switch (dir)
		{
		case 'n':
			if (n == 0)
			{
				answer = false;
			}
			break;
		case 's':
			if (s == 0)
			{
				answer = false;
			}
			break;
		case 'e':
			if (e == 0)
			{
				answer = false;
			}
			break;
		case 'w':
			if (w == 0)
			{
				answer = false;
			}
			break;
		}
		return answer;
		
		
	}
	
	
	
	
	

}
