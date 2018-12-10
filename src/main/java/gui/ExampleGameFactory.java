package gui;

import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.RenderLayer;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import com.almasb.fxgl.texture.Texture;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class ExampleGameFactory {

    public enum ExampleType {
        PLAYER,
        BALL,
        WALL,
        BRICK,
        PARTICLE
    }

    public static Entity newPlayer(double x, double y) {
        return Entities.builder()
                .at(x, y)
                .type(ExampleType.PLAYER)
                .bbox(new HitBox("Player", BoundingShape.box(100, 30)))
                .viewFromNode(new Rectangle(100, 30, Color.BLUE))
                .with( new CollidableComponent(true))
                .build();
    }

    public static Entity newBackground() {
        return Entities.builder()
                .viewFromNode(new Rectangle(800, 600, Color.WHITE))
                .renderLayer(RenderLayer.BACKGROUND)
                .build();
    }

    public static Entity newBall(double x, double y) {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setOnPhysicsInitialized(() -> physics.setLinearVelocity(0,0));
        physics.setBodyType(BodyType.DYNAMIC);
        physics.setFixtureDef(new FixtureDef().restitution(1f).density(0.1f).friction(0f));
        return Entities.builder()
                .at(x, y)
                .type(ExampleType.BALL)
                .bbox(new HitBox("Ball", BoundingShape.circle(10)))
                .viewFromNode(new Circle(10, Color.LIGHTCORAL))
                .with(physics, new CollidableComponent(true))
                .build();
    }

    public static Entity newWalls() {
        Entity walls = Entities.makeScreenBounds(100);
        walls.setType(ExampleType.WALL);
        walls.addComponent(new CollidableComponent(true));
        return walls;
    }

    public static Entity newBrick(int x, int y, String texture){
        PhysicsComponent physics = new PhysicsComponent();
        physics.setOnPhysicsInitialized(() -> physics.setLinearVelocity(0,0));
        physics.setBodyType(BodyType.STATIC);
        physics.setFixtureDef(new FixtureDef().restitution(1f).density(0.1f).friction(0f));
        return Entities.builder()
                .at(x, y)
                .type(ExampleType.BRICK)
                .bbox(new HitBox("Brick", BoundingShape.box(90, 40)))
                .viewFromTexture(texture)
                //.viewFromNode(new Rectangle(90, 40, color))
                .with(physics, new CollidableComponent(true))
                .build();
    }

    public static Entity newParticle(){
        Entity newEntity = new Entity();
        newEntity.setType(ExampleType.PARTICLE);
        return newEntity;
    }



}
