using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Web.Routing;

namespace Ice_Breaker_API
{
    public class RouteConfig
    {
        public static void RegisterRoutes(RouteCollection routes)
        {
            routes.IgnoreRoute("{resource}.axd/{*pathInfo}");

            routes.MapRoute(
                name: "Default",
                url: "{controller}/{action}/{id}",
                defaults: new { controller = "Home", action = "Add", id = UrlParameter.Optional }
            );

            routes.MapRoute(
                name: "GetUser",
                url: "v1/GetUser/{email}",
                defaults: new { controller = "v1", action = "GetUser"}
                );
        }
    }
}
