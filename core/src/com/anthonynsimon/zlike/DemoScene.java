package com.anthonynsimon.zlike;

import com.anthonynsimon.zlike.components.*;
import com.anthonynsimon.zlike.core.GameObject;
import com.anthonynsimon.zlike.core.Scene;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

import java.util.HashMap;

public class DemoScene {
    public static Scene create(TextureAtlas atlas) {
        Scene scene = new Scene("tilemap.tmx");

        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                GameObject floor = new GameObject();
                float x = (i - 25) * 16;
                float y = (j - 25) * 16;
                floor.transform.position.set(x, y, 0);
                int choice = (int) ((float) Math.random() * 3) + 1;
                floor.addComponent("texture", new TextureComponent(atlas.findRegion("floor", choice)));
                scene.gameObjects.add(floor);
            }
        }

        for (int i = 0; i < GameConf.testNPCs; i++) {
            GameObject npc = new GameObject();
            npc.transform.position.set(new Vector3((float) (Math.random() * 50f * 16) - (25f * 16f), (float) (Math.random() * 50f * 16f) - (25f * 16f), 0));
            npc.addComponent("movement", new MovementComponent());
            npc.addComponent("texture", new TextureComponent(null)); // idle anim is set right after this
            npc.addComponent("animation", new AnimationComponent("idle", new HashMap<String, Animation<TextureRegion>>() {{
                put("idle", new Animation<>(
                        0.1f,
                        atlas.findRegions("wizzard_m_idle_anim"),
                        Animation.PlayMode.LOOP
                ));
                put("running", new Animation<>(
                        0.1f,
                        atlas.findRegions("wizzard_m_run_anim"),
                        Animation.PlayMode.LOOP
                ));
            }}));
            npc.addComponent("randomWalk", new RandomWalkComponent(100f));
            npc.tags.add("player" + i);
            scene.gameObjects.add(npc);
        }

        GameObject player = new GameObject();
        player.transform.position.set(new Vector3(64, 64, 0));
        player.addComponent("playerInput", new PlayerInputComponent(100));
        player.addComponent("movement", new MovementComponent());
        player.addComponent("texture", new TextureComponent(null)); // idle anim is set right after this
        player.addComponent("animation", new AnimationComponent("idle", new HashMap<String, Animation<TextureRegion>>() {{
            put("idle", new Animation<>(
                    0.1f,
                    atlas.findRegions("elf_m_idle_anim"),
                    Animation.PlayMode.LOOP
            ));
            put("running", new Animation<>(
                    0.1f,
                    atlas.findRegions("elf_m_run_anim"),
                    Animation.PlayMode.LOOP
            ));
        }}));
        player.tags.add("player");
        scene.gameObjects.add(player);

        scene.camera.addComponent("followPlayer", new CameraFollowComponent(player, 2.8f));

        return scene;
    }
}
