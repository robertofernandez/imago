/**
 * 
 */
package util;

/**
 * @author Roberto G. Fernandez
 * 
 */
public class ImageCalculations {

    public static int[] rgb2hsl(int r, int g, int b) {

        float var_R = (r / 255f);
        float var_G = (g / 255f);
        float var_B = (b / 255f);

        float var_Min; // Min. value of RGB
        float var_Max; // Max. value of RGB
        float del_Max; // Delta RGB value

        if (var_R > var_G) {
            var_Min = var_G;
            var_Max = var_R;
        } else {
            var_Min = var_R;
            var_Max = var_G;
        }

        if (var_B > var_Max)
            var_Max = var_B;
        if (var_B < var_Min)
            var_Min = var_B;

        del_Max = var_Max - var_Min;

        float H = 0, S, L;
        L = (var_Max + var_Min) / 2f;

        if (del_Max == 0) {
            H = 0;
            S = 0;
        } // gray
        else { // Chroma
            if (L < 0.5)
                S = del_Max / (var_Max + var_Min);
            else
                S = del_Max / (2 - var_Max - var_Min);

            float del_R = (((var_Max - var_R) / 6f) + (del_Max / 2f)) / del_Max;
            float del_G = (((var_Max - var_G) / 6f) + (del_Max / 2f)) / del_Max;
            float del_B = (((var_Max - var_B) / 6f) + (del_Max / 2f)) / del_Max;

            if (var_R == var_Max)
                H = del_B - del_G;
            else if (var_G == var_Max)
                H = (1 / 3f) + del_R - del_B;
            else if (var_B == var_Max)
                H = (2 / 3f) + del_G - del_R;
            if (H < 0)
                H += 1;
            if (H > 1)
                H -= 1;
        }
        int[] hsl = new int[3];
        hsl[0] = (int) (360 * H);
        hsl[1] = (int) (S * 100);
        hsl[2] = (int) (L * 100);

        return hsl;
    }

    public static int[] apply_sobel(int[] src_1d, int width, int height, double sobscale, float offsetval) {
        int i_w = width;
        int i_h = height;

        int d_w;
        int d_h;
        int[] dest_1d;

        d_w = width;
        d_h = height;
        dest_1d = new int[d_w * d_h];

        for (int i = 0; i < src_1d.length; i++) {
            try {
                int a = src_1d[i] & 0x000000ff;
                int b = src_1d[i + 1] & 0x000000ff;
                int c = src_1d[i + 2] & 0x000000ff;
                int d = src_1d[i + i_w] & 0x000000ff;
                int e = src_1d[i + i_w + 2] & 0x000000ff;
                int f = src_1d[i + 2 * i_w] & 0x000000ff;
                int g = src_1d[i + 2 * i_w + 1] & 0x000000ff;
                int h = src_1d[i + 2 * i_w + 2] & 0x000000ff;
                int hor = (a + d + f) - (c + e + h);
                if (hor < 0)
                    hor = -hor;
                int vert = (a + b + c) - (f + g + h);
                if (vert < 0)
                    vert = -vert;
                short gc = (short) (sobscale * (hor + vert));
                gc = (short) (gc + offsetval);
                if (gc > 255)
                    gc = 255;
                dest_1d[i] = 0xff000000 | gc << 16 | gc << 8 | gc;

                // reached borders of image so goto next row
                // (see Convolution.java)
                if (((i + 3) % i_w) == 0) {
                    dest_1d[i] = 0;
                    dest_1d[i + 1] = 0;
                    dest_1d[i + 2] = 0;
                    i += 3;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                // if reached row boudary of image return.
                i = src_1d.length;
            }
        }
        return dest_1d;
    }
}
