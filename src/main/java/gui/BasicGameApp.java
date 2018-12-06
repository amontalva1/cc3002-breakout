package gui;

import com.almasb.fxgl.app.DSLKt;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.settings.GameSettings;
import facade.HomeworkTwoFacade;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import logic.brick.Brick;
import logic.level.Level;

import java.util.*;

import static gui.ExampleGameFactory.*;

public class BasicGameApp extends GameApplication {

    int gameState;
    HomeworkTwoFacade game;
    HashMap<Entity, Brick>bricksOnScreen;
    int[] posPlayer;
    private Text uiText;
    private Text uiText2;
    Level lastLevel;

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(800);
        gameSettings.setHeight(600);
        gameSettings.setTitle("Basic Game App");
        gameSettings.setVersion("0.1");

    }

    @Override
    protected void initGame() {
        posPlayer = new int[2];
        posPlayer[0] = 300;
        posPlayer[1] = 550;
        Entity player = newPlayer(posPlayer[0], posPlayer[1]);
        Entity ball = newBall(player.getX()+player.getWidth()/2, player.getY()-player.getHeight()/2);
        Entity bg = newBackground();
        Entity walls = newWalls();
        getGameWorld().addEntities(player, bg, ball, walls);
        game = new HomeworkTwoFacade();
        lastLevel = game.getCurrentLevel();
        gameState = 0;
        bricksOnScreen = new HashMap<>();

    }
    @Override
    protected void initUI(){
        uiText = new Text();
        uiText2 = new Text();
        uiText.setFont(Font.font(18));
        uiText2.setFont(Font.font(24));
        getGameScene().addUINode(uiText);
        getGameScene().addUINode(uiText2);
        DSLKt.centerText(uiText);
        DSLKt.centerText(uiText2);
        uiText.setText("0");
        uiText2.setY(uiText2.getY()+100);
        uiText2.setX(uiText2.getX()-100);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    protected void initInput() {
        Input input = getInput();
        input.addAction(new UserAction("Move Right") {
            @Override
            protected void onAction() {

                if (gameState != 5 && getGameWorld().getEntitiesByType(ExampleType.BALL).get(0).getComponent(PhysicsComponent.class).getVelocityY() == 0 &&
                        getGameWorld().getEntitiesByType(ExampleType.BALL).get(0).getComponent(PhysicsComponent.class).getVelocityX() == 0){
                    if(getGameWorld().getEntitiesByType(ExampleGameFactory.ExampleType.PLAYER).get(0).getX() < 700){
                        getGameWorld().getEntitiesByType(ExampleGameFactory.ExampleType.PLAYER)
                                .forEach(e -> e.translateX(5));
                        getGameWorld().getEntitiesByType(ExampleGameFactory.ExampleType.BALL)
                                .get(0).getComponent(PhysicsComponent.class).reposition(
                                        new Point2D(getGameWorld().getEntitiesByType(ExampleGameFactory.ExampleType.BALL)
                                                .get(0).getX()+5, getGameWorld().getEntitiesByType(ExampleGameFactory.ExampleType.BALL)
                                                .get(0).getY())
                        );
                        posPlayer[0]+=5;
                    }
                }

                else{
                    if(gameState != 0 && getGameWorld().getEntitiesByType(ExampleGameFactory.ExampleType.PLAYER).get(0).getX() < 700){
                        getGameWorld().getEntitiesByType(ExampleGameFactory.ExampleType.PLAYER)
                                .forEach(e -> e.translateX(5));
                    }
                    posPlayer[0]+=5;
                }
            }
        }, KeyCode.D);

        input.addAction(new UserAction("Move Left") {
            @Override
            protected void onAction() {
                if (gameState != 5 && getGameWorld().getEntitiesByType(ExampleType.BALL).get(0).getComponent(PhysicsComponent.class).getVelocityY() == 0 &&
                        getGameWorld().getEntitiesByType(ExampleType.BALL).get(0).getComponent(PhysicsComponent.class).getVelocityX() == 0){
                    if(getGameWorld().getEntitiesByType(ExampleGameFactory.ExampleType.PLAYER).get(0).getX() > 0){
                        getGameWorld().getEntitiesByType(ExampleGameFactory.ExampleType.PLAYER)
                                .forEach(e -> e.translateX(-5));
                        getGameWorld().getEntitiesByType(ExampleGameFactory.ExampleType.BALL)
                                .get(0).getComponent(PhysicsComponent.class).reposition(
                                new Point2D(getGameWorld().getEntitiesByType(ExampleGameFactory.ExampleType.BALL)
                                        .get(0).getX()-5, getGameWorld().getEntitiesByType(ExampleGameFactory.ExampleType.BALL)
                                        .get(0).getY())
                        );
                    }
                    posPlayer[0]-=5;
                }
                else{
                    if(gameState != 0 && getGameWorld().getEntitiesByType(ExampleGameFactory.ExampleType.PLAYER).get(0).getX() > 0){
                        getGameWorld().getEntitiesByType(ExampleGameFactory.ExampleType.PLAYER)
                                .forEach(e -> e.translateX(-5));
                    }
                    posPlayer[0]-=5;
                }
            }
        }, KeyCode.A);

        input.addAction(new UserAction("Init Game") {
            @Override
            protected void onAction(){
                if(gameState == 0 && bricksOnScreen.size()==0 && game.hasNextLevel()){
                    game.goNextLevel();
                    lastLevel = game.getCurrentLevel();
                    gameState = 1;
                    clearBricks();
                    setUpLevel();
                    getGameWorld().getEntitiesByType(ExampleType.BALL).get(0).getComponent(PhysicsComponent.class).setLinearVelocity(5 * 60, -5 * 60);
                }else if(gameState == 0 && bricksOnScreen.size() > 0){
                    gameState = 1;
                    getGameWorld().getEntitiesByType(ExampleType.BALL).get(0).getComponent(PhysicsComponent.class).setLinearVelocity(5 * 60, -5 * 60);
                }
            }

        }, KeyCode.SPACE);

        input.addAction(new UserAction("Add Level") {
            @Override
            protected void onAction(){
                game.addPlayingLevel(game.newLevelWithBricksFull("Add level", 4, 0.9, 0.1, 1));
            }

        }, KeyCode.N);

    }

    @Override
    protected void initPhysics() {
        getPhysicsWorld().setGravity(0,0);
        getPhysicsWorld().addCollisionHandler(
                new CollisionHandler(ExampleType.BALL, ExampleType.WALL) {
                    @Override
                    protected void onHitBoxTrigger(Entity ball, Entity wall,
                                                   HitBox boxBall, HitBox boxWall) {
                        if (boxWall.getName().equals("BOT")) {
                            ball.removeFromWorld();
                            game.dropBall();
                            if (game.isGameOver()) {
                                gameState = 0;

                            }else{
                                gameState = 0;
                                ball = newBall(posPlayer[0],posPlayer[1]);
                                getGameWorld().addEntity(ball);
                            }
                        }
                    }
                });
        getPhysicsWorld().addCollisionHandler(
                new CollisionHandler(ExampleType.BALL, ExampleType.WALL) {
                    @Override
                    protected void onHitBoxTrigger(Entity ball, Entity wall,
                                                   HitBox boxBall, HitBox boxWall) {
                        if (boxWall.getName().equals("TOP")) {
                            ball.getComponent(PhysicsComponent.class).setLinearVelocity(ball.getComponent(PhysicsComponent.class).getVelocityX(), -ball.getComponent(PhysicsComponent.class).getVelocityY());
                        }
                    }
                });
        getPhysicsWorld().addCollisionHandler(
                new CollisionHandler(ExampleType.BALL, ExampleType.WALL) {
                    @Override
                    protected void onHitBoxTrigger(Entity ball, Entity wall,
                                                   HitBox boxBall, HitBox boxWall) {
                        if (boxWall.getName().equals("LEFT")) {
                            ball.getComponent(PhysicsComponent.class).setLinearVelocity(-ball.getComponent(PhysicsComponent.class).getVelocityX(), ball.getComponent(PhysicsComponent.class).getVelocityY());
                        }
                    }
                });
        getPhysicsWorld().addCollisionHandler(
                new CollisionHandler(ExampleType.BALL, ExampleType.WALL) {
                    @Override
                    protected void onHitBoxTrigger(Entity ball, Entity wall,
                                                   HitBox boxBall, HitBox boxWall) {
                        if (boxWall.getName().equals("RIGHT")) {
                            ball.getComponent(PhysicsComponent.class).setLinearVelocity(-ball.getComponent(PhysicsComponent.class).getVelocityX(), ball.getComponent(PhysicsComponent.class).getVelocityY());
                        }
                    }
                });
        getPhysicsWorld().addCollisionHandler(
                new CollisionHandler(ExampleType.PLAYER, ExampleType.BALL) {
                    @Override
                    protected void onHitBoxTrigger(Entity player, Entity ball, HitBox boxPlayer, HitBox boxBall) {
                        ball.getComponent(PhysicsComponent.class).setLinearVelocity(ball.getComponent(PhysicsComponent.class).getVelocityX(), -ball.getComponent(PhysicsComponent.class).getVelocityY());
                    }
                }
        );
        getPhysicsWorld().addCollisionHandler(
                new CollisionHandler(ExampleType.BRICK, ExampleType.BALL) {
                    @Override
                    protected void onHitBoxTrigger(Entity brick, Entity ball, HitBox boxBrick, HitBox boxBall) {
                        ball.getComponent(PhysicsComponent.class).setLinearVelocity(ball.getComponent(PhysicsComponent.class).getVelocityX() > 0 ? 5*60 : -5*60, -ball.getComponent(PhysicsComponent.class).getVelocityY() > 0 ? 5*60 : -5*60);
                        System.out.println("El nivel previo al destruir es: " + game.getCurrentLevel());
                        bricksOnScreen.get(brick).hit();
                        if(bricksOnScreen.get(brick).isDestroyed()){
                            System.out.println("El nivel posterior al destruir es: " +game.getCurrentLevel());
                            brick.removeFromWorld();
                            bricksOnScreen.remove(brick);
                            if(game.getCurrentLevel() != lastLevel){
                                clearBricks();
                                lastLevel = game.getCurrentLevel();
                                setUpLevel();
                            }
                        }
                        uiText.setText(String.valueOf(game.getCurrentPoints()));
                        if(game.winner()){
                            DSLKt.centerText(uiText2);
                            uiText2.setText("GANASTEEEEEEEEEEEEEEEEEEEEE");
                        }
                    }
                }
        );

    }


    public void setUpLevel(){
        int x = 0;
        int y = 0;
        Collections.shuffle(game.getBricks());
        for(Brick b : game.getBricks()) {
            Entity brick;
            if (b.getScore() == 0){
                brick = newBrick(x,y, Color.GRAY);
            }else if(b.getScore() == 50){
                brick = newBrick(x,y, Color.LIGHTBLUE);
            }else{
                brick = newBrick(x,y, Color.BROWN);
            }
            bricksOnScreen.put(brick, b);
            getGameWorld().addEntity(brick);
            x+=brick.getWidth();
            if(x>=800){
                x = 0;
                y += brick.getHeight();
            }
        }
    }

    public void clearBricks(){
        getGameWorld().getEntitiesByType(ExampleType.BRICK).forEach(e -> e.removeFromWorld());
        bricksOnScreen.clear();
    }

}

