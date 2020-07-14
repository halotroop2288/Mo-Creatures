package drzhark.mocreatures.dimension;

import net.minecraft.block.Block;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.MoCreatures;

//this one is a copy of the end world provider

public class WorldProviderWyvernEnd extends WorldProvider
{
//    private IRenderHandler skyRenderer;

	/**
     * creates a new world chunk manager for WorldProvider
     */
    public void registerWorldChunkManager()
    {
        this.worldChunkMgr = new WorldChunkManagerWyvernLair(MoCreatures.WyvernLairBiome, 0.5F, 0.0F);
        this.dimensionId = MoCreatures.WyvernLairDimensionID;
        //MoCSkyRenderer mySkyRenderer = new MoCSkyRenderer();
        //setSkyRenderer(mySkyRenderer);
        //@SideOnly(Side.CLIENT)
        //setSkyRenderer(new MoCSkyRenderer());
        //this.hasNoSky = true;
        setCustomSky();
    }

    /**
     * Returns a new chunk provider which generates chunks for this world
     */
    public IChunkProvider createChunkGenerator()
    {
        return new MoCChunkProviderWyvernLair(this.worldObj, this.worldObj.getSeed(), 0);
    }
    
   
    private void setCustomSky()
    {
    	if (MoCreatures.isServer())
    	{
    		return;
    	}
    	setSkyRenderer(new MoCSkyRenderer());
    }
//    /**
//     * Calculates the angle of sun and moon in the sky relative to a specified time (usually worldTime)
//     */
//    @Override
//    public float calculateCelestialAngle(long par1, float par3)
//    {
//        return 0.0F;
//    }

    
    @SideOnly(Side.CLIENT)

    /**
     * Returns array with sunrise/sunset colors
     */
    @Override
    public float[] calcSunriseSunsetColors(float par1, float par2)
    {
        return null;
    }
    
//    @Override
//    protected void generateLightBrightnessTable()
//    {
//        float var1 = 0.1F;
//
//        for (int var2 = 0; var2 <= 15; ++var2)
//        {
//            float var3 = 1.0F - (float)var2 / 15.0F;
//            this.lightBrightnessTable[var2] = (1.0F - var3) / (var3 * 3.0F + 1.0F) * (1.0F - var1) + var1;
//        }
//    }
    
    
    @SideOnly(Side.CLIENT)

    /**
     * Return Vec3D with biome specific fog color
     */
    public Vec3 getFogColor(float par1, float par2)
    {
        int var3 = 10518688;
        float var4 = MathHelper.cos(par1 * (float)Math.PI * 2.0F) * 2.0F + 0.5F;

        if (var4 < 0.0F)
        {
            var4 = 0.0F;
        }

        if (var4 > 1.0F)
        {
            var4 = 1.0F;
        }

//        float var5 = (float)(var3 >> 16 & 255) / 255.0F;
//        float var6 = (float)(var3 >> 8 & 255) / 255.0F;
//        float var7 = (float)(var3 & 255) / 255.0F;
        
        float var5 = (float)(var3 >> 174 & 255) / 255.0F;
        float var6 = (float)(var3 >> 117 & 255) / 255.0F;
        float var7 = (float)(var3 >> 255 & 255) / 255.0F;
        
        var5 = 0/255.0F;
        var6 = 98/255.0F;
        var7 = 73/255.0F;
        
        var5 *= var4 * 0.0F + 0.15F;
        var6 *= var4 * 0.0F + 0.15F;
        var7 *= var4 * 0.0F + 0.15F;
        return this.worldObj.getWorldVec3Pool().getVecFromPool((double)var5, (double)var6, (double)var7);
    }

    @SideOnly(Side.CLIENT)
    public boolean isSkyColored()
    {
        return false;
    }

    /**
     * True if the player can respawn in this dimension (true = overworld, false = nether).
     */
    public boolean canRespawnHere()
    {
        return false;
    }

    /**
     * Returns 'true' if in the "main surface world", but 'false' if in the Nether or End dimensions.
     */
    public boolean isSurfaceWorld()
    {
        return true;
    }

    @SideOnly(Side.CLIENT)

    /**
     * the y level at which clouds are rendered.
     */
    public float getCloudHeight()
    {
        return 76.0F;
    }

    /**
     * Will check if the x, z position specified is alright to be set as the map spawn point
     */
    public boolean canCoordinateBeSpawn(int par1, int par2)
    {
        int var3 = this.worldObj.getFirstUncoveredBlock(par1, par2);
        return var3 == 0 ? false : Block.blocksList[var3].blockMaterial.blocksMovement();
    }

    /**
     * A Message to display to the user when they transfer out of this dismension.
     *
     * @return The message to be displayed
     */
    @Override
    public String getDepartMessage()
    {
           return "Leaving the Wyvern Lair";
    }
    
    @Override
    public String getWelcomeMessage()
    {
    	return "Entering the Wyvern Lair";
    }
    /**
     * Gets the hard-coded portal location to use when entering this dimension.
     */
    public ChunkCoordinates getEntrancePortalLocation()
    {
        return new ChunkCoordinates(0, 70, 0);
    }

    public int getAverageGroundLevel()
    {
        return 50;
    }

    @SideOnly(Side.CLIENT)

    /**
     * Returns true if the given X,Z coordinate should show environmental fog.
     */
    public boolean doesXZShowFog(int par1, int par2)
    {
        return true;//true;
    }

    /**
     * Returns the dimension's name, e.g. "The End", "Nether", or "Overworld".
     */
    public String getDimensionName()
    {
        return "Wyvern Lair";
    }
    
//    @SideOnly(Side.CLIENT)
//    @Override
//        public void setSkyRenderer(IRenderHandler skyRenderer)
//        {
//            this.skyRenderer = skyRenderer;
//       }
    
    
    
	 public String getSunTexture()
	 {
		 return "/mocreatures.twinsuns.png";//"/sunRed.png";
	 }

	 /*public String getMoonTexture()
	 {
	 return "/moonGreen.png";
	 }*/

	 public boolean renderStars()
	 {
	 return true;
	 }
	 
	 public boolean renderClouds()
	 {
	 return true;
	 }

	 //not active?
	
	 public boolean renderVoidFog()
	 {
		 return false;
	 }

	 public boolean renderEndSky()
	 {
	 return false;
	 }
	
	
	 public float setSunSize()
	 {
	 return 10.0F;
	 }

	 
	 public float setMoonSize()
	 {
	 return 0.5F;
	 }
	 
	 public float getStarBrightness(World world, float f)
	    {
	        return 1.0F;
	    }
	    
	    
	 
	@Override
		 public String getSaveFolder()
		 {
		        return "MoCWyvernLair";
		 }
		 
		@Override
		public double getMovementFactor()
	    {
			return 1.0;
	    }
}
