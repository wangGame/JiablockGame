#ifdef GL_ES
precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoord;
uniform sampler2D u_texture;
uniform vec2 u_region_min;
uniform vec2 u_region_max;
uniform float u_radius_norm;
uniform float u_smooth;

void main() {
    vec4 tex = texture2D(u_texture, v_texCoord) * v_color;
    vec2 local = (v_texCoord - u_region_min) / (u_region_max - u_region_min);

    float dx = min(local.x, 1.0 - local.x);
    float dy = min(local.y, 1.0 - local.y);

    float r = clamp(u_radius_norm, 0.0, 0.5);

    float alphaMask = 1.0;

    if (dx < r && dy < r) {
        float cx = r - dx;
        float cy = r - dy;
        float dist = sqrt(cx*cx + cy*cy);
        alphaMask = 1.0 - smoothstep(r, r + u_smooth, dist);
    }

    tex.a *= alphaMask;

    if (tex.a <= 0.001) discard;

    gl_FragColor = tex;
}
