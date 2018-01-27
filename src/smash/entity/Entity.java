package smash.entity;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Shape;

import general.Main;

public abstract class Entity {
	
	float x,y;
	double speedX,speedY,speed;
	int width,height;
	Shape hitbox;
	Image sprite;
	
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public double getSpeedX() {
		return speedX;
	}
	public void setSpeedX(double speedX) {
		this.speedX = speedX;
	}
	public double getSpeedY() {
		return speedY;
	}
	public void setSpeedY(double speedY) {
		this.speedY = speedY;
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
	public Shape getHitbox() {
		return hitbox;
	}
	public void setHitbox(Shape hitbox) {
		this.hitbox = hitbox;
	}
	public Image getSprite() {
		return sprite;
	}
	public void setSprite(Image sprite) {
		this.sprite = sprite;
	}
	
	
	public void move(int delta){
		x += speedX*delta;
		y += speedY*delta;
		hitbox.setLocation((float)x, (float)y);
	}
	
	public abstract void checkForCollision();
	
	public abstract void die();
}
