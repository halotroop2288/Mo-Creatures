package drzhark.mocreatures.dimension;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenTower extends WorldGenerator {

    private Block MainBlock;
    private Block brickBlock;

    public WorldGenTower(Block Main, Block Brick, Block Deco) {
        this.MainBlock = Main;
        this.brickBlock = Brick;
    }

    public WorldGenTower(Block Main, int MainMeta, Block Brick, int BrickMeta, Block Deco, int DecoMeta) {
        this.MainBlock = Main;
        this.brickBlock = Brick;
    }

    @Override
    public boolean generate(World world, Random rand, BlockPos pos) {
        int t = 3;
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        if (!world.isAirBlock(new BlockPos(x, y - 1, z))) {
            for (int i = 0; i < 9; i++) {
                if (world.isAirBlock(new BlockPos(x + 4 - i, y - 1, z))) {
                    return false;
                }
            }
            for (int Ny = y - 3; Ny < y + 21; Ny++) {
                for (int Nz = z - 5; Nz < z + 6; Nz = Nz + 10) {
                    for (int Nx = x - 2; Nx < x + 3; Nx++) {
                        BlockPos pos1 = new BlockPos(Nx, Ny, Nz);
                        world.setBlockState(pos1, this.MainBlock.getDefaultState(), t);
                    }
                }
                world.setBlockState(new BlockPos(x - 3, Ny, z - 4), this.MainBlock.getDefaultState(), t);
                world.setBlockState(new BlockPos(x + 3, Ny, z - 4), this.MainBlock.getDefaultState(), t);
                world.setBlockState(new BlockPos(x - 4, Ny, z - 3), this.MainBlock.getDefaultState(), t);
                world.setBlockState(new BlockPos(x + 4, Ny, z - 3), this.MainBlock.getDefaultState(), t);
                world.setBlockState(new BlockPos(x - 3, Ny, z + 4), this.MainBlock.getDefaultState(), t);
                world.setBlockState(new BlockPos(x + 3, Ny, z + 4), this.MainBlock.getDefaultState(), t);
                world.setBlockState(new BlockPos(x - 4, Ny, z + 3), this.MainBlock.getDefaultState(), t);
                world.setBlockState(new BlockPos(x + 4, Ny, z + 3), this.MainBlock.getDefaultState(), t);

                for (int Nx = x - 5; Nx < x + 6; Nx = Nx + 10) {
                    for (int Nz = z - 2; Nz < z + 3; Nz++) {
                        world.setBlockState(new BlockPos(Nx, Ny, Nz), this.MainBlock.getDefaultState(), t);
                    }
                }
            }

            for (int Nx = x - 3; Nx < x + 4; Nx++) {
                for (int Nz = z - 3; Nz < z + 4; Nz++) {
                    world.setBlockState(new BlockPos(Nx, y - 1, Nz), Blocks.flowing_lava.getDefaultState(), t);
                }
            }

            for (int Ny = y; Ny < y + 24; Ny = Ny + 8) {
                world.setBlockState(new BlockPos(x - 1, Ny, z - 4), this.brickBlock.getDefaultState(), t);
                world.setBlockState(new BlockPos(x - 2, Ny, z - 4), this.brickBlock.getDefaultState(), t);
                world.setBlockState(new BlockPos(x - 4, Ny + 1, z - 1), this.brickBlock.getDefaultState(), t);
                world.setBlockState(new BlockPos(x - 4, Ny + 1, z - 2), this.brickBlock.getDefaultState(), t);
                world.setBlockState(new BlockPos(x - 4, Ny + 2, z + 1), this.brickBlock.getDefaultState(), t);
                world.setBlockState(new BlockPos(x - 4, Ny + 2, z + 2), this.brickBlock.getDefaultState(), t);
                world.setBlockState(new BlockPos(x - 2, Ny + 3, z + 4), this.brickBlock.getDefaultState(), t);
                world.setBlockState(new BlockPos(x - 1, Ny + 3, z + 4), this.brickBlock.getDefaultState(), t);
                world.setBlockState(new BlockPos(x + 1, Ny + 4, z + 4), this.brickBlock.getDefaultState(), t);
                world.setBlockState(new BlockPos(x + 2, Ny + 4, z + 4), this.brickBlock.getDefaultState(), t);
                world.setBlockState(new BlockPos(x + 4, Ny + 5, z + 2), this.brickBlock.getDefaultState(), t);
                world.setBlockState(new BlockPos(x + 4, Ny + 5, z + 1), this.brickBlock.getDefaultState(), t);
                world.setBlockState(new BlockPos(x + 4, Ny + 6, z - 1), this.brickBlock.getDefaultState(), t);
                world.setBlockState(new BlockPos(x + 4, Ny + 6, z - 2), this.brickBlock.getDefaultState(), t);
                world.setBlockState(new BlockPos(x + 2, Ny + 7, z - 4), this.brickBlock.getDefaultState(), t);
                world.setBlockState(new BlockPos(x + 1, Ny + 7, z - 4), this.brickBlock.getDefaultState(), t);
            }
            return true;
        }
        return false;
    }
}
