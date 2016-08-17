package org.jcommon.com.facebook.object;

/**
 * 
 * @author leolee
 *        "width": 947,
          "height": 1446,
          "render_as_sticker": false,
          "url": "https://scontent.xx.fbcdn.net/v/t35.0-12/13843455_1012493775536035_2142056052_o.png?oh=a8dd08caf63f9ad6b7b147b637bff3f2&oe=579D32EF",
          "preview_url": "https://scontent.xx.fbcdn.net/v/t34.0-0/p206x206/13871782_1012493775536035_2142056052_n.png?oh=f584dd8e252275d335d68187c3b65e42&oe=579C213B",
          "image_type": 1,
          "max_width": 947,
          "max_height": 1446
 */
public class ImageData extends JsonObject {
	private int width;
	private int height;
	private boolean render_as_sticker;
	private String url;
	private String preview_url;
	private int image_type;
	private int max_width;
	private int max_height;
	
	public ImageData(String json, boolean decode) {
		super(json, decode);
		// TODO Auto-generated constructor stub
	}
	
	public ImageData(String json) {
		super(json, true);
		// TODO Auto-generated constructor stub
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public boolean isRender_as_sticker() {
		return render_as_sticker;
	}

	public void setRender_as_sticker(boolean render_as_sticker) {
		this.render_as_sticker = render_as_sticker;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPreview_url() {
		return preview_url;
	}

	public void setPreview_url(String preview_url) {
		this.preview_url = preview_url;
	}

	public int getImage_type() {
		return image_type;
	}

	public void setImage_type(int image_type) {
		this.image_type = image_type;
	}

	public int getMax_width() {
		return max_width;
	}

	public void setMax_width(int max_width) {
		this.max_width = max_width;
	}

	public int getMax_height() {
		return max_height;
	}

	public void setMax_height(int max_height) {
		this.max_height = max_height;
	}
}
