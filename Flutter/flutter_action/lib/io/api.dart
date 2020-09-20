import 'dart:convert';
import 'dart:io';
import 'dart:convert' show utf8;

const apiCategory = {
   'name':'Currency',
   'route':'currency'
};

class Api{
  final HttpClient _httpClient = HttpClient();

  final String _url = 'flutter.udacity.com';

  Future<List> getUnits (String category) async{
    final uri = Uri.http(_url, '/$category');
    final jsonResponse = await _getJson(uri);
    if(jsonResponse == null || jsonResponse['units'] == null){
      print('Error retrieving units.');
      return null;
    }
    return jsonResponse['units'];
  }

  Future<double> convert(String category,String amount,String fromUnit,String toUnit) async{
     final uri = Uri.http(_url, '/$category/convert',{'amount':amount,'from':fromUnit,'to':toUnit});
     final jsonResponse = await _getJson(uri);
     if(jsonResponse == null || jsonResponse['status'] == null){
       print('Error retrieving conversion.');
       return null;
     }else if(jsonResponse['status'] == 'error'){
       print(jsonResponse['message']);
       return null;
     }
     return jsonResponse['conversion'].toDouble();

  }

  Future<Map<String,dynamic>> _getJson(Uri uri) async{
    try{
        final httpRequest = await _httpClient.getUrl(uri);
        final HttpResponse = await httpRequest.close();
        if(HttpResponse.statusCode != HttpStatus.OK){
          return null;
        }
        final responseBody = await HttpResponse.transform(utf8.decoder).join();
        return json.decode(responseBody);
    }on Exception catch(e){
      print('$e');
      return null;
    }
  }
}