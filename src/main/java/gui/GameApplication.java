package gui;

import com.almasb.fxgl.app.DSLKt;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.particle.ParticleComponent;
import com.almasb.fxgl.particle.ParticleEmitter;
import com.almasb.fxgl.particle.ParticleEmitters;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.settings.GameSettings;
import facade.HomeworkTwoFacade;
import javafx.geometry.Point2D;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import logic.brick.Brick;
import logic.level.Level;

import java.util.*;

import static gui.GameFactory.*;

/**
 * GameApplication has all the control of the graphical
 * user interface of the Breakout Game, it doesnt modify any
 * of the logic of the game but makes use of the HomeworkTwoFacade
 * class in which you can easily access to pretty much every logic component
 * of the game. It has 2 access other classes of the logic package but in a
 * slightly way, in order to fix the problem of advancing level easily.
 * @author Antonio Montalva
 */

public class GameApplication extends com.almasb.fxgl.app.GameApplication {

    private int gameState;
    private int gameLost;
    private HomeworkTwoFacade game;
    private HashMap<Entity, Brick>bricksOnScreen;
    private Text uiText;
    private Text uiText2;
    private Text uiText3;
    private Level lastLevel;

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(800);
        gameSettings.setHeight(600);
        gameSettings.setTitle("Game App");
        gameSettings.setVersion("1.0");

    }

    @Override
    protected void initGame() {
        Entity player = newPlayer(300, 550);
        Entity ball = newBall(player.getX()+player.getWidth()/2, player.getY()-player.getHeight()/2);
        Entity bg = newBackground();
        Entity walls = newWalls();
        getGameWorld().addEntities(player, bg, ball, walls);
        game = new HomeworkTwoFacade();
        lastLevel = game.getCurrentLevel();
        gameState = 0;
        gameLost = 0;
        bricksOnScreen = new HashMap<>();
        getGameWorld().selectedEntityProperty().addListener((o, oldEntity, newEntity) -> {
            hit(getGameWorld().getSelectedEntity().get());
        });
    }
    @Override
    protected void initUI(){
        uiText = new Text();
        uiText2 = new Text();
        uiText3 = new Text();
        uiText.setFont(Font.font(18));
        uiText2.setFont(Font.font(24));
        uiText2.setFont(Font.font(22));
        getGameScene().addUINode(uiText);
        getGameScene().addUINode(uiText2);
        getGameScene().addUINode(uiText3);
        DSLKt.centerText(uiText);
        DSLKt.centerText(uiText2);
        DSLKt.centerText(uiText3);
        uiText.setText("0");
        uiText2.setY(uiText2.getY()+100);
        uiText2.setX(uiText2.getX()-100);
        uiText3.setX(uiText2.getX()-300);
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

                if (gameLost != 1 && gameState != 5 && getGameWorld().getEntitiesByType(EntityType.BALL).get(0).getComponent(PhysicsComponent.class).getVelocityY() == 0 &&
                        getGameWorld().getEntitiesByType(EntityType.BALL).get(0).getComponent(PhysicsComponent.class).getVelocityX() == 0){
                    if(getGameWorld().getEntitiesByType(GameFactory.EntityType.PLAYER).get(0).getX() < 700){
                        getGameWorld().getEntitiesByType(GameFactory.EntityType.PLAYER)
                                .forEach(e -> e.translateX(5));
                        getGameWorld().getEntitiesByType(GameFactory.EntityType.BALL)
                                .get(0).getComponent(PhysicsComponent.class).reposition(
                                        new Point2D(getGameWorld().getEntitiesByType(GameFactory.EntityType.BALL)
                                                .get(0).getX()+5, getGameWorld().getEntitiesByType(GameFactory.EntityType.BALL)
                                                .get(0).getY())
                        );
                    }
                }

                else{
                    if(gameLost != 1 && gameState != 0 && getGameWorld().getEntitiesByType(GameFactory.EntityType.PLAYER).get(0).getX() < 700){
                        getGameWorld().getEntitiesByType(GameFactory.EntityType.PLAYER)
                                .forEach(e -> e.translateX(5));
                    }
                }
            }
        }, KeyCode.D);

        input.addAction(new UserAction("Move Left") {
            @Override
            protected void onAction() {
                if (gameLost != 1 && gameState != 5 && getGameWorld().getEntitiesByType(EntityType.BALL).get(0).getComponent(PhysicsComponent.class).getVelocityY() == 0 &&
                        getGameWorld().getEntitiesByType(EntityType.BALL).get(0).getComponent(PhysicsComponent.class).getVelocityX() == 0){
                    if(getGameWorld().getEntitiesByType(GameFactory.EntityType.PLAYER).get(0).getX() > 0){
                        getGameWorld().getEntitiesByType(GameFactory.EntityType.PLAYER)
                                .forEach(e -> e.translateX(-5));
                        double posPlayerX = getGameWorld().getEntitiesByType(EntityType.PLAYER).get(0).getX() + getGameWorld().getEntitiesByType(EntityType.PLAYER).get(0).getWidth()/2;
                        double posPlayerY = getGameWorld().getEntitiesByType(EntityType.PLAYER).get(0).getY()-getGameWorld().getEntitiesByType(EntityType.PLAYER).get(0).getHeight()/2;
                        getGameWorld().getEntitiesByType(GameFactory.EntityType.BALL)
                                .get(0).getComponent(PhysicsComponent.class).reposition(
                                new Point2D(posPlayerX, posPlayerY)
                        );
                    }
                }
                else{
                    if(gameLost != 1 && gameState != 0 && getGameWorld().getEntitiesByType(GameFactory.EntityType.PLAYER).get(0).getX() > 0){
                        getGameWorld().getEntitiesByType(GameFactory.EntityType.PLAYER)
                                .forEach(e -> e.translateX(-5));
                    }
                }
            }
        }, KeyCode.A);

        input.addAction(new UserAction("Init Game") {
            @Override
            protected void onAction(){
                uiText3.setText("Balls: " + game.getBallsLeft());
                if(gameLost != 1 && gameState == 0 && bricksOnScreen.size() > 0){
                    gameState = 1;
                    getGameWorld().getEntitiesByType(EntityType.BALL).get(0).getComponent(PhysicsComponent.class).setLinearVelocity(5 * 60, -5 * 60);
                }
            }

        }, KeyCode.SPACE);

        input.addAction(new UserAction("Restart Game") {
            @Override
            protected void onAction(){
                getGameWorld().clear();
                getGameScene().clear();
                initUI();
                initGame();
            }
        }, KeyCode.R);

        input.addAction(new UserAction("Add Level") {
            @Override
            protected void onAction(){
                game.addPlayingLevel(game.newLevelWithBricksFull("Add level", 20, 0.6, 0.2, 1));

                if(gameLost != 1 && gameState == 0 && bricksOnScreen.size()==0 && game.hasNextLevel()){
                    game.goNextLevel();
                    lastLevel = game.getCurrentLevel();
                    clearBricks();
                    setUpLevel();
                }
        }}, KeyCode.N);


    }

    @Override
    protected void initPhysics() {
        getPhysicsWorld().setGravity(0,0);
        getPhysicsWorld().addCollisionHandler(
                new CollisionHandler(EntityType.BALL, EntityType.WALL) {
                    @Override
                    protected void onHitBoxTrigger(Entity ball, Entity wall,
                                                   HitBox boxBall, HitBox boxWall) {
                        if (boxWall.getName().equals("BOT")) {
                            ball.removeFromWorld();
                            game.dropBall();
                            getAudioPlayer().playSound("ded.mp3");
                            uiText3.setText("Balls: " + game.getBallsLeft());
                            if (game.isGameOver()) {
                                gameState = 0;
                                gameLost = 1;
                                uiText2.setText("Perdiste :'(, presiona R para reiniciar");
                            }else{
                                gameState = 0;
                                double posPlayerX = getGameWorld().getEntitiesByType(EntityType.PLAYER).get(0).getX() + getGameWorld().getEntitiesByType(EntityType.PLAYER).get(0).getWidth()/2;
                                double posPlayerY = getGameWorld().getEntitiesByType(EntityType.PLAYER).get(0).getY()-getGameWorld().getEntitiesByType(EntityType.PLAYER).get(0).getHeight()/2;
                                ball = newBall(posPlayerX, posPlayerY);
                                getGameWorld().addEntity(ball);
                            }
                        }else{
                            getAudioPlayer().playSound("ballhit.wav");
                        }
                    }
                });

        getPhysicsWorld().addCollisionHandler(
                new CollisionHandler(EntityType.PLAYER, EntityType.BALL) {
                    @Override
                    protected void onHitBoxTrigger(Entity player, Entity ball, HitBox boxPlayer, HitBox boxBall) {
                        if(ball.getComponent(PhysicsComponent.class).getVelocityY() != 0){
                            getAudioPlayer().playSound("ballhit.wav");
                        }
                        ball.getComponent(PhysicsComponent.class).setLinearVelocity(ball.getComponent(PhysicsComponent.class).getVelocityX(), -ball.getComponent(PhysicsComponent.class).getVelocityY());
                    }
                }
        );
        getPhysicsWorld().addCollisionHandler(
                new CollisionHandler(EntityType.BRICK, EntityType.BALL) {
                    @Override
                    protected void onHitBoxTrigger(Entity brick, Entity ball, HitBox boxBrick, HitBox boxBall) {
                        hit(brick);
                    }
                }
        );

    }

    public void hit(Entity brick){
        bricksOnScreen.get(brick).hit();
        hitParticle(brick.getX(), brick.getY());
        if(bricksOnScreen.get(brick).isDestroyed()){
            getAudioPlayer().playSound("glasshit.wav");
            brick.removeFromWorld();
            bricksOnScreen.remove(brick);
            if(game.getCurrentLevel() != lastLevel){
                clearBricks();
                lastLevel = game.getCurrentLevel();
                setUpLevel();
            }
        }else{
            if(bricksOnScreen.get(brick).getScore() == 0){
                getAudioPlayer().playSound("metalhit.wav");
            }else{
                getAudioPlayer().playSound("woodenhit.wav");
            }
            if(bricksOnScreen.get(brick).remainingHits() == 7){
                brick.setViewFromTexture("metalbrick2.png");
            }else if(bricksOnScreen.get(brick).remainingHits() == 3){
                brick.setViewFromTexture("metalbrick3.png");
            }else if(bricksOnScreen.get(brick).remainingHits() == 1 && bricksOnScreen.get(brick).getScore() == 200){
                brick.setViewFromTexture("woodenbrick2.png");
            }
        }
        uiText.setText(String.valueOf(game.getCurrentPoints()));
        uiText3.setText("Balls: " + game.getBallsLeft());
        if(game.winner()){
            DSLKt.centerText(uiText2);
            getAudioPlayer().playMusic("victory.mp3");
            gameLost = 1;
            getGameWorld().getEntitiesByType(EntityType.BALL).get(0).getComponent(PhysicsComponent.class).setLinearVelocity(0,0);
            uiText2.setText("GANASTEEEEEEEEEE, R para reiniciar");
        }
    }

    public void setUpLevel(){
        int x = 0;
        int y = 0;
        Collections.shuffle(game.getBricks());
        for(Brick b : game.getBricks()) {
            Entity brick;
            if (b.getScore() == 0){
                brick = newBrick(x,y, "metalbrick1.png");
            }else if(b.getScore() == 50){
                brick = newBrick(x,y, "glassbrick.png");
            }else{
                brick = newBrick(x,y, "woodenbrick1.png");
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
        getGameWorld().getEntitiesByType(EntityType.BRICK).forEach(e -> e.removeFromWorld());
        bricksOnScreen.clear();
    }

    public void hitParticle(double x, double y){
        Entity explosion = newParticle();
        explosion.setPosition(x,y);

        ParticleEmitter emitter = ParticleEmitters.newExplosionEmitter(1);
        emitter.setBlendMode(BlendMode.SRC_OVER);

        ParticleComponent component = new ParticleComponent(emitter);

        component.setOnFinished(explosion::removeFromWorld);

        explosion.addComponent(component);

        getGameWorld().addEntity(explosion);
    }


}

