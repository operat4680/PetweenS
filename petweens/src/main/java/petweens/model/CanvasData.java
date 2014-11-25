package petweens.model;

public class CanvasData {
	private int currentPage;
	private String canvasAlldata;
	public CanvasData(int currentPage,String canvasAlldata){
		this.currentPage = currentPage;
		this.canvasAlldata = canvasAlldata;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public String getCanvasAlldata() {
		return canvasAlldata;
	}
	public void setCanvasAlldata(String canvasAlldata) {
		this.canvasAlldata = canvasAlldata;
	}
}
