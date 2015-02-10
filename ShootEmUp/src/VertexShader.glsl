#version 330

layout(location = 0) in vec2 pos;
layout (location = 1) in vec2 tex;


uniform mat4 modelMatrix;
uniform mat4 projectionMatrix;

out vec2 TexCoord;

void main()
{
    
    gl_Position =  modelMatrix * vec4(pos.xy, 0.0f, 1.0f);
    TexCoord = tex;
}