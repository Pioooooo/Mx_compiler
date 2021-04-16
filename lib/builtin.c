#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <stdbool.h>
char* __g_malloc(int size){
    return malloc(size);
}
void g_print(char* str){
    printf("%s",str);
}
void g_println(char* str){
    printf("%s\n",str);
}
void g_printInt(int n){
    printf("%d",n);
}
void g_printlnInt(int n){
    printf("%d\n",n);
}
char* g_getString(){
    char* a=malloc(sizeof(char)*2333);
    scanf("%s",a);
    return a;
}
int g_getInt(){
    int a;
    scanf("%d",&a);
    return a;
}
char* g_toString(int i){
    char* a=malloc(sizeof(char)*23);
    sprintf(a,"%d",i);
    return a;
}
int c_string_length(char* str){
    return strlen(str);
}
char* c_string_substring(char* str,int left,int right){
    char* a=malloc(sizeof(char)*(right-left+1));
    memcpy(a,str+left,sizeof(char)*(right-left));
    a[right-left]='\0';
    return a;
}
int c_string_parseInt(char* str){
    int a;
    sscanf(str,"%d",&a);
    return a;
}
int c_string_ord(char* str,int pos){
    return str[pos];
}
char* __g_str_add(char* l,char* r){
    char* a=malloc(sizeof(char)*2333);
    strcpy(a,l);
    strcat(a,r);
    return a;
}
bool __g_str_lt(char* l,char* r){
    return strcmp(l,r)<0;
}
bool __g_str_gt(char* l,char* r){
    return strcmp(l,r)>0;
}
bool __g_str_le(char* l,char* r){
    return strcmp(l,r)<=0;
}
bool __g_str_ge(char* l,char* r){
    return strcmp(l,r)>=0;
}
bool __g_str_eq(char* l,char* r){
    return strcmp(l,r)==0;
}
bool __g_str_ne(char* l,char* r){
    return strcmp(l,r)!=0;
}