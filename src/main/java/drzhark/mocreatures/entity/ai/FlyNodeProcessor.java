package drzhark.mocreatures.entity.ai;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.pathfinder.NodeProcessor;

public class FlyNodeProcessor extends NodeProcessor {

    public void initProcessor(IBlockAccess iblockaccessIn, Entity entityIn) {
        super.initProcessor(iblockaccessIn, entityIn);
    }

    /**
     * This method is called when all nodes have been processed and PathEntity is created.
     * {@link net.minecraft.world.pathfinder.WalkNodeProcessor WalkNodeProcessor} uses this to change its field {@link
     * net.minecraft.world.pathfinder.WalkNodeProcessor#avoidsWater avoidsWater}
     */
    public void postProcess() {
        super.postProcess();
    }

    /**
     * Returns given entity's position as PathPoint
     */
    public PathPoint getPathPointTo(Entity entityIn) {
        return this.openPoint(MathHelper.floor_double(entityIn.getEntityBoundingBox().minX),
                MathHelper.floor_double(entityIn.getEntityBoundingBox().minY + 0.5D), MathHelper.floor_double(entityIn.getEntityBoundingBox().minZ));
    }

    /**
     * Returns PathPoint for given coordinates
     *  
     * @param entityIn entity which size will be used to center position
     * @param x target x coordinate
     * @param y target y coordinate
     * @param target z coordinate
     */
    public PathPoint getPathPointToCoords(Entity entityIn, double x, double y, double target) {
        return this.openPoint(MathHelper.floor_double(x - (double) (entityIn.width / 2.0F)), MathHelper.floor_double(y + 0.5D),
                MathHelper.floor_double(target - (double) (entityIn.width / 2.0F)));
    }

    public int findPathOptions(PathPoint[] pathOptions, Entity entityIn, PathPoint currentPoint, PathPoint targetPoint, float maxDistance) {
        int i = 0;
        EnumFacing[] aenumfacing = EnumFacing.values();
        int j = aenumfacing.length;

        for (int k = 0; k < j; ++k) {
            EnumFacing enumfacing = aenumfacing[k];
            PathPoint pathpoint2 =
                    this.getSafePoint(entityIn, currentPoint.xCoord + enumfacing.getFrontOffsetX(),
                            currentPoint.yCoord + enumfacing.getFrontOffsetY(), currentPoint.zCoord + enumfacing.getFrontOffsetZ());

            if (pathpoint2 != null && !pathpoint2.visited && pathpoint2.distanceTo(targetPoint) < maxDistance) {
                pathOptions[i++] = pathpoint2;
            }
        }

        return i;
    }

    /**
     * Returns a point that the entity can safely move to
     */
    private PathPoint getSafePoint(Entity entityIn, int x, int y, int z) {
        int l = this.func_176186_b(entityIn, x, y, z);
        return l == -1 ? this.openPoint(x, y, z) : null;
    }

    private int func_176186_b(Entity entityIn, int x, int y, int z) {
        for (int l = x; l < x + this.entitySizeX; ++l) {
            for (int i1 = y; i1 < y + this.entitySizeY; ++i1) {
                for (int j1 = z; j1 < z + this.entitySizeZ; ++j1) {
                    BlockPos blockpos = new BlockPos(l, i1, j1);
                    Block block = this.blockaccess.getBlockState(blockpos).getBlock();

                    if (block.getMaterial() != Material.AIR) {
                        return 0;
                    }
                }
            }
        }

        return -1;
    }
}
