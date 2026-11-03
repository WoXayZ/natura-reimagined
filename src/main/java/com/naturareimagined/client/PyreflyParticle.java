package com.naturareimagined.client;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;

public class PyreflyParticle extends TextureSheetParticle {

    private final SpriteSet sprites;

    protected PyreflyParticle(ClientLevel level, double x, double y, double z, double dx, double dy, double dz, SpriteSet sprites) {
        super(level, x, y, z);
        this.sprites = sprites;
        this.lifetime = 55 + this.random.nextInt(40);
        this.quadSize = 0.16F + this.random.nextFloat() * 0.1F;
        this.gravity = 0.0F;
        this.hasPhysics = false;
        this.xd = dx;
        this.yd = dy;
        this.zd = dz;
        this.setSpriteFromAge(sprites);
    }

    @Override
    public void tick() {
        super.tick();
        this.xd += (this.random.nextDouble() - 0.5) * 0.006;
        this.zd += (this.random.nextDouble() - 0.5) * 0.006;
        this.setSpriteFromAge(this.sprites);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static final class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double dx, double dy, double dz) {
            return new PyreflyParticle(level, x, y, z, dx, dy, dz, this.sprites);
        }
    }
}
