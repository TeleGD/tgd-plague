package theEnglishSStone;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import app.AppFont;
import app.AppLoader;

public class TextField {

	private float aspectRatio;
	private int x;
	private int y;
	private int width;
	private int height;
	private int cornerRadius;
	private int borderWidth;
	private int padding;
	private int line;
	private Font font;
	private Color backgroundColor;
	private Color borderColor;
	private Color textColor;
	private Color caretColor;
	private String text;
	private int caretBlinkPeriod;
	private int caretBlinkCountdown;
	private int caret;

	public TextField(float aspectRatio, int x, int y, int width, int height, int cornerRadius, int borderWidth) {
		this.aspectRatio = aspectRatio;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.cornerRadius = cornerRadius;
		this.borderWidth = borderWidth;
		this.padding = (this.height - this.borderWidth * 2) / 10;
		this.line = (this.height - this.borderWidth * 2) - this.padding;
		this.font = AppLoader.loadFont("/fonts/vt323.ttf", AppFont.PLAIN, (int) (this.line * aspectRatio));
		this.backgroundColor = new Color(1f, 1f, 1f);
		this.borderColor = new Color(.6f, .6f, .6f);
		this.textColor = new Color(.2f, .2f, .2f);
		this.caretColor = new Color(1f, 0f, 0f);
		this.text = "";
		this.caretBlinkPeriod = 2000;
		this.caretBlinkCountdown = 0;
		this.caret = 0;
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {
		this.caretBlinkCountdown = (this.caretBlinkCountdown + this.caretBlinkPeriod - delta) % this.caretBlinkPeriod;
	}

	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		int borderWidth = this.borderWidth;
		int halfBorderWidth = borderWidth / 2;
		int padding = this.padding;
		context.setColor(this.backgroundColor);
		context.fillRoundRect((this.x + halfBorderWidth) * this.aspectRatio, (this.y + halfBorderWidth) * this.aspectRatio, (this.width - borderWidth) * this.aspectRatio, (this.height - borderWidth) * this.aspectRatio, (int) ((this.cornerRadius - halfBorderWidth) * this.aspectRatio));
		context.setColor(this.borderColor);
		context.setLineWidth(this.borderWidth);
		context.drawRoundRect((this.x + halfBorderWidth) * this.aspectRatio, (this.y + halfBorderWidth) * this.aspectRatio, (this.width - borderWidth) * this.aspectRatio, (this.height - borderWidth) * this.aspectRatio, (int) ((this.cornerRadius - halfBorderWidth) * this.aspectRatio));
		context.setColor(this.textColor);
		context.setFont(this.font);
		context.drawString(this.text, (int) ((this.x + borderWidth + padding) * this.aspectRatio), (int) ((this.y + borderWidth + padding) * this.aspectRatio));
		if (this.caretBlinkCountdown >= this.caretBlinkPeriod / 2) {
			int x = this.x + borderWidth + padding + (int) (this.font.getWidth(text.substring(0, this.caret)) / this.aspectRatio);
			int y1 = this.y + borderWidth + padding;
			int y2 = y1 + this.line;
			context.setColor(caretColor);
			context.drawLine(x * this.aspectRatio, y1 * this.aspectRatio, x * this.aspectRatio, y2 * this.aspectRatio);
		}
	}

	public void setText(String text) {
		this.text = text;
		this.caretBlinkCountdown = 0;
	}

	public String getText() {
		return this.text;
	}

	public void setCaret(int caret) {
		this.caret = caret;
		this.caretBlinkCountdown = 0;
	}

	public int getCaret() {
		return this.caret;
	}

}
