package itemDetail.subviews;

public class SimpleDetailModel implements PlaceDetailSubviewModel {
	private String data;
	private int type;
	private int number;

	public SimpleDetailModel(String data, int type) {
		this.data = data;
		this.type = type;
	}

	public SimpleDetailModel(String data, int type, int number) {
		this.data = data;
		this.type = type;
		this.number = number;
	}

	@Override
	public int getType() {
		return type;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
}
