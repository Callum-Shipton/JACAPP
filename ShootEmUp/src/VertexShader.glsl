#version 430 core
layout (location = 0) in vec2 pos; // <vec2 position>
layout (location = 1) in vec2 tex;

out vec2 TexCoords;

uniform mat4 model;
uniform mat4 projection;

void main()
{
    TexCoords = tex.xy;
    gl_Position = projection * model * vec4(pos.xy, 0.0, 1.0);
}