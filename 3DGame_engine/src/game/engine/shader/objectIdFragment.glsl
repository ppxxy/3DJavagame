#version 400 core

flat in int pass_objectId;

out int out_Color;

void main(void){

	out_Color = pass_objectId;

}