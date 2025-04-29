<#import "template.ftl" as layout>
<@layout.html>
    <div class="custom-login-container">
        <img src="${url.resourcesPath}/img/logo.png" class="custom-logo">
        <#if message?has_content>
            <div class="alert alert-${message.type}">${message.summary}</div>
        </#if>
        <form action="${url.loginAction}" method="post">
            <!-- Ваши кастомные поля -->
        </form>
    </div>
</@layout.html>