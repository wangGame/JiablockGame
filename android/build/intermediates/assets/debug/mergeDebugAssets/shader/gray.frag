#ifdef GL_ES
precision mediump float;
#endif


//input from vertex shader
varying vec4 v_color;
varying vec2 v_textCoords;
uniform sampler2D u_texture;


void main() {
    vec4 tempColor = v_color* texture2D(u_texture,v_textCoords);
    // float c=(tempColor.r + tempColor.g + tempColor.b)/3.0;
    float c = tempColor.r*0.35+tempColor.g*0.59+tempColor.b*0.11;
    c = c ;
    gl_FragColor = vec4(c, c, c, tempColor.a);
}