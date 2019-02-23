/*
 * This class generates a Gaussian Kernel
 * Users have to define the radius r of the kernel, which is going to generate a r x r
 * matrix for discrete gaussian filter
 * In this class, we assume that 2D gaussian filter is preferred
 * For convenience, r should be odd, but not even
 * The standard deviation should be specified by user also
 * Numbers in each element will be calculated by the function calcGauss()
 * 
 * */
public class GaussKernel {
	/**
	 * @variable r: integer identicates the coverage
	 * @variable kernel: a double matrix scaning the src pictures
	 * @variable processedColor: this matrix records the new color values of the processed picture
	 * */
	private int r;
	private double[][] kernel;
	private int[][] processedColor;

	/**
	 * This method aims calculating the spatial gauss, sigma being assumed to be an integer
	 * @param x, y are the spatial coordinates of a pixel
	 * */
	private double calcGauss(int x, int y, int sigma) {
		double distance = x ^ 2 + y ^ 2;
		double spreade = (2 * sigma * sigma);
		return Math.exp(- distance / spreade) / 2 * Math.PI * (sigma ^ 2) ;
	}
	
	private void buildKernel(int radius, int sigma) {
		r = radius;
		
		// Make sure the matrix has an odd length
		kernel = new double[2 * r + 1][2 * r + 1];
		
		// Get the sum of the matrix for normalization
		double sum = 0.0;
		
		for (int i = 0; i < kernel.length; i++) {
			for (int j = 0; j < kernel[0].length; j++) {
				kernel[i][j] = calcGauss(i - r, j - r, sigma);
				System.out.println("kernel[" + i + "][" + j +"]: " + kernel[i][j]);
				sum += kernel[i][j];
			}
		}
		System.out.println("sum is " + sum);
		// Normalizing the matrix
		for (int i = 0; i < kernel.length; i++) {
			for (int j = 0; j < kernel[0].length; j++) {
				kernel[i][j] /= sum;
				System.out.println("normalized kernel[" + i + "][" + j +"]: " + kernel[i][j]);

			}
		}
		
		// return kernel;
	}
	
	public int[][] processPic(int[][] color, int radius, int sigma) {
		processedColor = new int[color.length][color[0].length];
		buildKernel(radius, sigma);
		
		// int[] x_window = null;
		System.out.println("original y size is " + color.length);
		System.out.println("original x size is " + color[0].length);

		// window_***_index setting the boarder for the window
		// this nested loop is sliding the window
		// the window starts moving from (0, 0) of the color matrix
		// but for the window itself, it starts from (r, r), the center of
		// this matrix. with the window sliding, the cover area of the windwo increases
		for (int i = 0; i < color.length; i++) {
			//int window_upper_index = Math.min(0, Math.max(0, i - r));
			//int window_lower_index = Math.min(color.length - 1, i + r);
			for (int j = 0; j < color[0].length; j++) {
				//int window_left_index = Math.min(0, Math.max(0,  j - r)) ;
				//int window_right_index = Math.min(color[0].length - 1, j + r);
				//int x_pointer = window_left_index;
				//int y_pointer = window_upper_index;
				int left_board, upper_board = Integer.MIN_VALUE;
				int rignt_board, lower_board = Integer.MAX_VALUE;
				if (j <= r) {
					left_board = Math.abs(j - r);
					rignt_board = 2 * r;
				} else if (color[0].length - 1 - j <= r) {
					left_board = 0;
					rignt_board = r + (color[0].length - 1 - j);
				} else {
					left_board = 0;
					rignt_board = 2 * r;
				}
				if (i <= r) {
					upper_board = Math.abs(i - r);
					lower_board = 2 * r;
				} else if (color.length - 1 - i <= r) {
					upper_board = 0;
					lower_board = r + (color.length - 1 - i);
				} else {
					upper_board = 0;
					lower_board = 2 * r;
				}
				int y_pointer = upper_board;
				int x_pointer = left_board;
				while (y_pointer <= lower_board && y_pointer < kernel[0].length) {
					
					while (x_pointer <= rignt_board && x_pointer < kernel.length) {
						//System.out.println("x is " + x_pointer);
						//System.out.println("y is " + y_pointer);
						double kernel_cell = kernel[y_pointer][x_pointer];
						int color_x_pointer = -1;
						int color_y_pointer = -1;
						if (left_board == 0) {
							color_x_pointer = Math.min(x_pointer + j, color[0].length - 1);
						} else {
							color_x_pointer = x_pointer;
						}
						if (upper_board == 0) {
							color_y_pointer = Math.min(y_pointer + i, color.length - 1);
						} else {
							color_y_pointer = y_pointer;
						}
						//System.out.println("color x is " + color_x_pointer);
						//System.out.println("color y is " + color_y_pointer);
						int original_color = color[color_y_pointer][color_x_pointer];
						//System.out.println("kernel cell = " + kernel_cell);
						//System.out.println("original color " + original_color);
						double transfered = kernel_cell * original_color;
						//System.out.println("transfered value is " + transfered);
						processedColor[i][j] += transfered;
						//System.out.println("stored value is " + processedColor[i][j]);
						x_pointer++;
						
					}
					x_pointer = left_board;
					y_pointer++;
				}
				

			}
		}
		return processedColor;
		
	}

}
