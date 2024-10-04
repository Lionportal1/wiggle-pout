package adventure;

public class MapBlock {
	private String title = "";
	private String desc = "";
	private byte n = 1;
	private byte s = 1;
	private byte e = 1;
	private byte w = 1;
	
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

}
