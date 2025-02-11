package com.github.alexthe668.cloudstorage.client.render;

import com.github.alexthe668.cloudstorage.entity.BalloonCargoEntity;
import com.github.alexthe668.cloudstorage.entity.BalloonTieEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.LeashKnotModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.decoration.LeashFenceKnotEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public class RenderBalloonCargo extends EntityRenderer<BalloonCargoEntity> {

    public RenderBalloonCargo(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.5F;
    }

    public void render(BalloonCargoEntity cargo, float f1, float f2, PoseStack stack, MultiBufferSource source, int i) {
        BlockState blockstate = cargo.getBlockState();
        Level level = cargo.getLevel();
        if (blockstate != level.getBlockState(cargo.blockPosition()) && blockstate.getRenderShape() != RenderShape.INVISIBLE) {
            stack.pushPose();
            stack.translate(-0.5D, 0, -0.5D);
            if(blockstate.getRenderShape() == RenderShape.ENTITYBLOCK_ANIMATED && blockstate.hasProperty(HorizontalDirectionalBlock.FACING)){
                float f = blockstate.getValue(HorizontalDirectionalBlock.FACING).toYRot();
                stack.translate(0.5D, 0.5D, 0.5D);
                stack.mulPose(Vector3f.YP.rotationDegrees(-f));
                stack.translate(-0.5D, -0.5D, -0.5D);
            }
            Minecraft.getInstance().getBlockRenderer().renderSingleBlock(blockstate, stack, source, i, OverlayTexture.NO_OVERLAY);

            stack.popPose();
            super.render(cargo, f1, f2, stack, source, i);
        }
    }

    public ResourceLocation getTextureLocation(BalloonCargoEntity p_114632_) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
