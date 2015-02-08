#version 150 core

in vec2 pos;
in vec2 tex;

out vec2 TexCoords;

uniform mat4 model;
uniform mat4 projection;

void main()
{
    TexCoords = tex.xy;
    gl_Position =  model * vec4(pos.xy, 0.0, 1.0);
}